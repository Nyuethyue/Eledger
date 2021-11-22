package bhutan.eledger.domain.epayment.paymentadvice;

import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import lombok.Data;

import java.math.BigDecimal;

@Data(staticConstructor = "of")
public class PayableLine {
    private final Long id;
    private final EpGLAccount glAccount;
    private final BigDecimal paidAmount;
    private final BigDecimal amount;

    public static PayableLine withoutId(EpGLAccount glAccount, BigDecimal paidAmount, BigDecimal amount) {
        return new PayableLine(
                null,
                glAccount,
                paidAmount,
                amount
        );
    }

    public BigDecimal getAmountToBePaid() {
        return amount.compareTo(paidAmount) > 0 ? amount.subtract(paidAmount) : BigDecimal.ZERO;
    }
}
