package io.lastwill.eventscan.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "exchange_request")
@Getter
public class ExchangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "duc_address")
    private String ducAddress;

    @Column(name = "ducx_address")
    private String ducxAddress;

    @Column(name = "btc_address")
    private String btcAddress;

    @Column(name = "eth_address")
    private String ethAddress;


}
