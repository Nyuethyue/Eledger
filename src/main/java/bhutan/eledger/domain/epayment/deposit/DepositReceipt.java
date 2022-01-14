package bhutan.eledger.domain.epayment.deposit;

import bhutan.eledger.domain.epayment.payment.FlatReceipt;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepositReceipt {
    private final Long id;
    private final Long receiptId;
    private final Long depositId;

    private final FlatReceipt receipt;


    public static DepositReceipt withoutId(Long receiptId) {
        return new DepositReceipt(
                null,
                receiptId,
                null,
                null
        );
    }

    public static DepositReceipt withId(
            Long id,
            Long receiptId,
            Long depositId,
            FlatReceipt receipt) {
        return new DepositReceipt(
                id,
                receiptId,
                depositId,
                receipt
        );
    }
}
