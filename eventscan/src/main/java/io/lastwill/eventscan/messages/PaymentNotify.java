package io.lastwill.eventscan.messages;

import io.lastwill.eventscan.model.CryptoCurrency;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

@ToString(callSuper = true)
@Getter
public class PaymentNotify extends BaseNotify {
    private final String address;
    private final BigInteger amount;
    private final CryptoCurrency currency;
    private final boolean isSuccess;
    private final long exchangeId;

    public PaymentNotify(long exchangeId, String address, BigInteger amount, PaymentStatus status, String txHash, CryptoCurrency currency, boolean isSuccess) {
        super(status, txHash);
        this.exchangeId = exchangeId;
        this.address = address;
        this.amount = amount;
        this.currency = currency;
        this.isSuccess = isSuccess;
    }

    @Override
    public String getType() {
        return "payment";
    }
}
