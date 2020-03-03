package io.lastwill.eventscan.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "transfer")
@Getter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "exchange_id", referencedColumnName = "id")
    private Exchange exchangeId;

    @Column(name = "tx_hash")
    private String txHash;

    @Column(name = "amount")
    private BigInteger amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

}
