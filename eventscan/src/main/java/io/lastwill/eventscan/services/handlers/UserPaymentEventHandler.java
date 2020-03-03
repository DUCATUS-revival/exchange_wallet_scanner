package io.lastwill.eventscan.services.handlers;

import io.lastwill.eventscan.events.model.UserPaymentEvent;
import io.lastwill.eventscan.messages.PaymentNotify;
import io.lastwill.eventscan.messages.PaymentStatus;
import io.lastwill.eventscan.services.ExternalNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserPaymentEventHandler {
    @Autowired
    private ExternalNotifier externalNotifier;

    @EventListener
    private void userPaymentEventHandler(final UserPaymentEvent event) {
        try {
            externalNotifier.send(
                    event.getNetworkType(),
                    new PaymentNotify(
                            event.getAmount(),
                            PaymentStatus.COMMITTED,
                            event.getTransaction().getHash(),
                            event.getCurrency(),
                            event.isSuccess()
                            )
            );
        } catch (Throwable e) {
            log.error("Sending notification about transition confirmed are failed.", e);
        }
    }
}
