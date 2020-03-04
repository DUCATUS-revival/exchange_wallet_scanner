package io.lastwill.eventscan.repositories;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.Exchange;
import io.lastwill.eventscan.model.TransactionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.Collection;
import java.util.List;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {
    List<Exchange> findAllByStatus(@Param("status") TransactionStatus status);

    @Query("select c from Exchange c where c.receiveAddress in :receiveAddress and c.status = :status and c.fromCurrency = :fromCurrency")
    List<Exchange> findByRxAddress(@Param("receiveAddress") Collection<String> receiveAddress,
                                   @Param("status") TransactionStatus status,
                                   @Param("fromCurrency") CryptoCurrency fromCurrency);

    //List<Exchange> getUserAddressExchange(@Param("status") TransactionStatus status);
}
