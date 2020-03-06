package io.lastwill.eventscan.repositories;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.TransactionStatus;
import io.lastwill.eventscan.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends CrudRepository<Transfer, Long> {
    List<Transfer> findAllByStatusAndCurrency(@Param("status") TransactionStatus status, @Param("currency") CryptoCurrency currency);
}
