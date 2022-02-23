package bhutan.eledger.domain.epayment.paymentadvice;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public class PayableLine {
    private final Long id;
    private final EpGLAccount glAccount;
    private BigDecimal paidAmount;
    private BigDecimal amount;
    private BigDecimal toBePaidAmount;
    private final Long elTransactionId;

    void updateAmount(BigDecimal amount) {
        this.amount = amount;

        recalculateToBePaidAmount();
    }

    public BigDecimal getToBePaidAmount() {
        if (toBePaidAmount == null) {
            recalculateToBePaidAmount();
        }

        return toBePaidAmount;
    }

    @Deprecated(forRemoval = true)
    public BigDecimal getAmountToBePaid() {
        return getToBePaidAmount();
    }

    public boolean isPaid() {
        return amount.compareTo(paidAmount) == 0;
    }

    public boolean isNotPaid() {
        return !isPaid();
    }

    void pay() {
        pay(getToBePaidAmount());
    }

    void pay(BigDecimal paidAmount) {
        checkPayableLine(paidAmount);

        this.paidAmount = paidAmount.add(paidAmount);

        recalculateToBePaidAmount();
    }

    private void recalculateToBePaidAmount() {
        toBePaidAmount = amount.subtract(paidAmount);
    }

    private void checkPayableLine(BigDecimal paidAmount) {
        if (isPaid()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "payments.payableLineId",
                                    "The payable line by GL code: [" + getGlAccount().getCode() + "] has been already payed."
                            )
            );
        }

        if (getToBePaidAmount().compareTo(paidAmount) != 0) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "paidAmount",
                                    "Paid amount must be equal to be paid amount. Amount to be paid: " + getToBePaidAmount() + ", Paid amount: " + paidAmount
                            )
            );
        }
    }

    public static PayableLine withoutId(
            EpGLAccount glAccount,
            BigDecimal amount,
            Long elTransactionId
    ) {
        return new PayableLine(
                null,
                glAccount,
                BigDecimal.ZERO,
                amount,
                null,
                elTransactionId
        );
    }

    public static PayableLine withId(
            Long id,
            EpGLAccount glAccount,
            BigDecimal paidAmount,
            BigDecimal amount,
            BigDecimal toBePaidAmount,
            Long elTransactionId
    ) {
        return new PayableLine(
                id,
                glAccount,
                paidAmount,
                amount,
                toBePaidAmount,
                elTransactionId
        );
    }
}
