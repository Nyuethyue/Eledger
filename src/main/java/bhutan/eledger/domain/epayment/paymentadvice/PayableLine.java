package bhutan.eledger.domain.epayment.paymentadvice;

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
    private final BigDecimal amount;
    private final Long elTransactionId;

    public static PayableLine withoutId(
            EpGLAccount glAccount,
            BigDecimal paidAmount,
            BigDecimal amount,
            Long elTransactionId
    ) {
        return new PayableLine(
                null,
                glAccount,
                paidAmount,
                amount,
                elTransactionId
        );
    }

    public static PayableLine withId(
            Long id,
            EpGLAccount glAccount,
            BigDecimal paidAmount,
            BigDecimal amount,
            Long elTransactionId
    ) {
        return new PayableLine(
                id,
                glAccount,
                paidAmount,
                amount,
                elTransactionId
        );
    }

    public BigDecimal getAmountToBePaid() {
        return amount.compareTo(paidAmount) > 0 ? amount.subtract(paidAmount) : BigDecimal.ZERO;
    }

    public boolean isPaid() {
        return amount.compareTo(paidAmount) == 0;
    }

    public void pay(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
}
