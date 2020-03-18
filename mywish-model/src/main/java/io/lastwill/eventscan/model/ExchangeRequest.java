package io.lastwill.eventscan.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exchange_requests_exchangerequest")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRequest that = (ExchangeRequest) o;
        return Objects.equals(ducAddress, that.ducAddress) &&
                Objects.equals(ducxAddress, that.ducxAddress) &&
                Objects.equals(btcAddress, that.btcAddress) &&
                Objects.equals(ethAddress, that.ethAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ducAddress, ducxAddress, btcAddress, ethAddress);
    }
}
