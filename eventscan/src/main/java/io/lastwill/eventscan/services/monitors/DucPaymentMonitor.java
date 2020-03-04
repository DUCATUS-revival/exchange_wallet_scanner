package io.lastwill.eventscan.services.monitors;

import io.lastwill.eventscan.events.model.UserPaymentEvent;
import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.model.TransactionStatus;
import io.lastwill.eventscan.repositories.ExchangeRepository;
import io.mywish.blockchain.WrapperOutput;
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
public class DucPaymentMonitor {
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private EventPublisher eventPublisher;

    @EventListener
    private void handleBtcBlock(NewBlockEvent event) {

        if (event.getNetworkType() != NetworkType.DUCATUS_MAINNET) {
            return;
        }
        Set<String> addresses = event.getTransactionsByAddress().keySet();
        if (addresses.isEmpty()) {
            return;
        }
        exchangeRepository.findByRxAddress(addresses, TransactionStatus.WAITING, CryptoCurrency.DUC)
                .forEach(exchangeDetails -> {
                    List<WrapperTransaction> txes = event.getTransactionsByAddress().get(exchangeDetails.getReceiveAddress());
                    if (txes == null) {
                        //log.warn("There is no PaymentDetails entity found for DUC address {}.", paymentDetails.getRxAddress());
                        return;
                    }

                    for (WrapperTransaction tx: txes) {
                        for (WrapperOutput output: tx.getOutputs()) {
                            if (output.getParentTransaction() == null) {
                                log.warn("Skip it. Output {} has not parent transaction.", output);
                                continue;
                            }
                            if (!output.getAddress().equalsIgnoreCase(exchangeDetails.getReceiveAddress())) {
                                continue;
                            }

//                            log.warn("VALUE: {}", output.getValue());
//                            log.warn("VALUE: {}", exchangeDetails.getAmount());

                            if (output.getValue().equals(exchangeDetails.getAmount())) {
                                eventPublisher.publish(
                                        new UserPaymentEvent(
                                                NetworkType.DUCATUS_MAINNET,
                                                tx,
                                                output.getValue(),
                                                CryptoCurrency.DUC,
                                                true
                                        ));

                                log.warn("\u001B[32m"+ "|{}| {} DUC RECEIVED !" + "\u001B[0m",
                                        exchangeDetails.getReceiveAddress(),
                                        output.getValue());

                            }

                        }
                    }
                });
    }
}
