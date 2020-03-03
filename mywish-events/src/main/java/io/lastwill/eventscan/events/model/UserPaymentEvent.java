package io.lastwill.eventscan.events.model;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.mywish.blockchain.WrapperTransaction;
import io.lastwill.eventscan.model.NetworkType;
import lombok.Getter;
import java.math.BigInteger;

@Getter
public class UserPaymentEvent extends PaymentEvent {

    public UserPaymentEvent(NetworkType networkType,
                            WrapperTransaction transaction,
                            BigInteger amount,
                            CryptoCurrency currency,
                            boolean isSuccess) {
        super(networkType, transaction, transaction.getSingleOutputAddress(), amount, currency, isSuccess);
    }
}
