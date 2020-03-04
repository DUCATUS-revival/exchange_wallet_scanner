package io.lastwill.eventscan.repositories;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.Exchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.Collection;
import java.util.List;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {
    @Query("select c from Exchange c where c.receiveAddress = :receiveAddress and c.fromCurrency = :fromCurrency")
    List<Exchange> findByRxAddress(@Param("receiveAddress") Collection<String> receiveAddress,@Param("fromCurrency") CryptoCurrency fromCurrency);
}
