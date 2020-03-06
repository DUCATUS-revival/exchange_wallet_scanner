package io.lastwill.eventscan.messages;

import io.lastwill.eventscan.model.CryptoCurrency;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class TransferConfirmNotify extends BaseNotify {
    private final long transferId;
    private final boolean isSuccess;
    private final CryptoCurrency currency;

    public TransferConfirmNotify(CryptoCurrency currency, String txHash, long transferId, boolean isSuccess) {
        super(PaymentStatus.COMMITTED, txHash);
        this.currency = currency;
        this.transferId = transferId;
        this.isSuccess = isSuccess;
    }

    @Override
    public String getType() {
        return "transferred";
    }
}
