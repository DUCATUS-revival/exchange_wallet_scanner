package io.lastwill.eventscan.services.monitors.transitions;

import io.lastwill.eventscan.events.model.TransferConfirmEvent;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.model.TransactionStatus;
import io.lastwill.eventscan.repositories.TransferRepository;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractConfirmationMonitor {
    private final TransferRepository transferRepository;
    private final NetworkType networkType;
    private final EventPublisher publisher;

    public AbstractConfirmationMonitor(
            TransferRepository transferRepository,
            EventPublisher publisher,
            NetworkType networkType) {
        this.transferRepository = transferRepository;
        this.networkType = networkType;
        this.publisher = publisher;
    }

    @EventListener(NewBlockEvent.class)
    public void doScan(NewBlockEvent event) {
        if (event.getNetworkType() != networkType) {
            return;
        }
        List<String> txHashes = getTxHashes(event);
        if (txHashes.isEmpty()) {
            return;
        }
        transferRepository
                .findAllByStatus(TransactionStatus.WAITING_FOR_CONFIRMATION)
                .stream()
                .filter(entry -> txHashes.contains(entry.getTxHash()))
                .forEach(entry -> {
                    log.debug("{}: Transaction {} confirmed", this.getClass().getSimpleName(), entry.getTxHash());
                    publisher.publish(new TransferConfirmEvent(networkType, entry.getId(), entry.getTxHash(), true));
                });
    }

    protected List<String> getTxHashes(NewBlockEvent newBlockEvent) {
        return newBlockEvent
                .getTransactionsByAddress()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .map(WrapperTransaction::getHash)
                .distinct()
                .collect(Collectors.toList());
    }
}
