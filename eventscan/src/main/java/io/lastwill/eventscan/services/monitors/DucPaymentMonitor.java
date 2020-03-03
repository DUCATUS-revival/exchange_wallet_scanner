package io.lastwill.eventscan.services.monitors;

import io.lastwill.eventscan.events.model.UserPaymentEvent;
import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.NetworkType;
import io.mywish.blockchain.WrapperOutput;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Slf4j
@Component
public class DucPaymentMonitor {

    @Autowired
    private EventPublisher eventPublisher;

    @Value("${io.lastwill.eventscan.duc.storage}")
    private String ducStorageAddress;

    @EventListener
    private void handleBtcBlock(NewBlockEvent event) {
        if (event.getNetworkType() != NetworkType.DUCATUS_MAINNET) {
            return;
        }

        List<WrapperTransaction> txes = event.getTransactionsByAddress().get(ducStorageAddress);
        if (txes == null || txes.isEmpty()) {
            //log.warn("There is no PaymentDetails entity found for DUC address {}.", paymentDetails.getRxAddress());
            return;
        }

        for (WrapperTransaction tx : txes) {
            for (WrapperOutput output : tx.getOutputs()) {
                if (output.getParentTransaction() == null) {
                    log.warn("Skip it. Output {} has not parent transaction.", output);
                    continue;
                }
                if (!output.getAddress().equalsIgnoreCase(ducStorageAddress)) {
                    continue;
                }

                if (0 == output.getValue().compareTo(BigInteger.ZERO)) {
                    continue;
                }

                eventPublisher.publish(
                        new UserPaymentEvent(
                                NetworkType.DUCATUS_MAINNET,
                                tx,
                                output.getValue(),
                                CryptoCurrency.DUC,
                                true
                        ));

                log.warn("\u001B[32m" + "|DUCATUS STORAGE| {} DUC RECEIVED !" + "\u001B[0m", output.getValue());

            }
        }
    }
}
