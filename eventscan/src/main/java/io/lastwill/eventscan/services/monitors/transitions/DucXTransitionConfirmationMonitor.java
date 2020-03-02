package io.lastwill.eventscan.services.monitors.transitions;

import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.repositories.TransferRepository;
import io.mywish.scanner.services.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DucXTransitionConfirmationMonitor extends AbstractConfirmationMonitor {
    public DucXTransitionConfirmationMonitor(
            @Autowired TransferRepository transferRepository,
            @Autowired EventPublisher publisher) {
        super(transferRepository, publisher, NetworkType.DUCATUSX_MAINNET);
    }
}
