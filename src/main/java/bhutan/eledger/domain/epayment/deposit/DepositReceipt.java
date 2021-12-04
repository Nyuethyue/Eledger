package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;


@Data
public class DepositReceipt {
    private final Long id;
    private final Long receiptId;
    private final Long depositId;

    public static DepositReceipt withoutId(Long receiptId, Long depositId) {
        return new DepositReceipt(
                null,
                receiptId,
                depositId
        );
    }

    public static DepositReceipt withId(
            Long id,
            Long receiptId,
            Long depositId) {
        return new DepositReceipt(
                id,
                receiptId,
                depositId
        );
    }
}
