package io.lastwill.eventscan.services.monitors;

import io.lastwill.eventscan.events.model.UserPaymentEvent;
import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.ExchangeRequest;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.repositories.ExchangRequestRepository;
import io.lastwill.eventscan.services.TransactionProvider;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class EthPaymentMonitor {
    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private ExchangRequestRepository exchangRequestRepository;

    @Autowired
    private TransactionProvider transactionProvider;

    @EventListener
    private void onNewBlockEvent(NewBlockEvent event) {
        if (event.getNetworkType() != NetworkType.ETHEREUM_MAINNET) {
            return;
        }

        Set<String> addresses = event.getTransactionsByAddress().keySet();
        if (addresses.isEmpty()) {
            return;
        }

        List<ExchangeRequest> exchangeDetails = exchangRequestRepository.findByEthRxAddress(addresses);
        for (ExchangeRequest exchangeDetailsETH : exchangeDetails) {
            final List<WrapperTransaction> transactions = event.getTransactionsByAddress().get(
                    exchangeDetailsETH.getEthAddress().toLowerCase()
            );

            if (transactions == null || transactions.isEmpty()) {
                log.error("User {} received from DB, but was not found in transaction list (block    {}).", exchangeDetailsETH, event.getBlock().getNumber());
                continue;
            }

            transactions.forEach(transaction -> {
                if (!exchangeDetailsETH.getEthAddress().equalsIgnoreCase(transaction.getOutputs().get(0).getAddress())) {
                    log.debug("Found transaction out from internal address. Skip it.");
                    return;
                }

                transactionProvider.getTransactionReceiptAsync(event.getNetworkType(), transaction)
                        .thenAccept(receipt -> {
                            eventPublisher.publish(new UserPaymentEvent(
                                    exchangeDetailsETH.getEthAddress(),
                                    NetworkType.ETHEREUM_MAINNET,
                                    transaction,
                                    transaction.getOutputs().get(0).getValue(),
                                    CryptoCurrency.ETH,
                                    true
                            ));
                        })
                        .exceptionally(throwable -> {
                            log.error("UserPaymentEvent handling failed.", throwable);
                            return null;
                        });

                log.warn("\u001B[32m" + "|{}| {} ETH RECEIVED!" + "\u001B[0m",
                        exchangeDetailsETH.getEthAddress(),
                        transaction.getOutputs().get(0).getValue());

            });
        }
    }
}
