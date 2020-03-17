package io.lastwill.eventscan.model;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "transfers_ducatustransfer")
@Getter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tx_hash")
    private String txHash;

    @Column(name = "amount")
    private BigInteger amount;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CryptoCurrency currency;

}
