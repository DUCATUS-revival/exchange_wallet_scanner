package io.lastwill.eventscan.events.model;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.mywish.blockchain.WrapperTransaction;
import io.lastwill.eventscan.model.NetworkType;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class UserPaymentEvent extends BaseEvent {
    private final long exchangeId;
    private final String address;
    private final WrapperTransaction transaction;
    private final BigInteger amount;
    private final CryptoCurrency currency;
    private final boolean isSuccess;

    public UserPaymentEvent(long exchangeId,
                            String address,
                            NetworkType networkType,
                            WrapperTransaction transaction,
                            BigInteger amount,
                            CryptoCurrency currency,
                            boolean isSuccess) {
        super(networkType);
        this.exchangeId = exchangeId;
        this.address = address;
        this.transaction = transaction;
        this.amount = amount;
        this.currency = currency;
        this.isSuccess = isSuccess;
    }
}
