package io.lastwill.eventscan.repositories;


import io.lastwill.eventscan.model.ExchangeRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ExchangRequestRepository extends CrudRepository<ExchangeRequest, Long> {
    @Query("select c from ExchangeRequest c where c.ducAddress in :ducAddress")
    List<ExchangeRequest> findByDucRxAddress(@Param("ducAddress") Collection<String> ducAddress);

    @Query("select c from ExchangeRequest c where c.ducxAddress in :ducxAddress")
    List<ExchangeRequest> findByDucxRxAddress(@Param("ducxAddress") Collection<String> ducxAddress);

    @Query("select c from ExchangeRequest c where c.btcAddress in :btcAddress")
    List<ExchangeRequest> findByBtcRxAddress(@Param("btcAddress") Collection<String> btcAddress);

    @Query("select c from ExchangeRequest c where c.ethAddress in :ethAddress")
    List<ExchangeRequest> findByEthRxAddress(@Param("ethAddress") Collection<String> ethAddress);

}
