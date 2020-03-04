package io.lastwill.eventscan.model;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "exchange")
@Getter
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "amount")
    private BigInteger amount;

    @Column(name = "from_curr")
    @Enumerated(EnumType.STRING)
    private CryptoCurrency fromCurrency;

    @Column(name = "to_curr")
    @Enumerated(EnumType.STRING)
    private CryptoCurrency toCurrency;

    @Column(name = "tx_hash")
    private String txHash;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
