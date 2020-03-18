package io.lastwill.eventscan.repositories;


import io.lastwill.eventscan.model.ExchangeRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

public interface ExchangRequestRepository extends CrudRepository<ExchangeRequest, Long> {
    @Query("select c from ExchangeRequest c where c.ducAddress in :ducAddress")
    Set<ExchangeRequest> findByDucRxAddress(@Param("ducAddress") Collection<String> ducAddress);

    @Query("select c from ExchangeRequest c where c.ducxAddress in :ducxAddress")
    Set<ExchangeRequest> findByDucxRxAddress(@Param("ducxAddress") Collection<String> ducxAddress);

    @Query("select c from ExchangeRequest c where c.btcAddress in :btcAddress")
    Set<ExchangeRequest> findByBtcRxAddress(@Param("btcAddress") Collection<String> btcAddress);

    @Query("select c from ExchangeRequest c where c.ethAddress in :ethAddress")
    Set<ExchangeRequest> findByEthRxAddress(@Param("ethAddress") Collection<String> ethAddress);

}
