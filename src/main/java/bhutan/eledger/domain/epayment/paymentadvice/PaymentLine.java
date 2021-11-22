package bhutan.eledger.domain.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

import java.math.BigDecimal;

@Data(staticConstructor = "of")
public class PaymentLine {
    private final Long id;
    private final GLAccount glAccount;
    private final BigDecimal paidAmount;
    private final BigDecimal amount;

    @Data(staticConstructor = "withId")
    public static class GLAccount {
        private final Long id;
        private final String code;

        private final Multilingual description;

        public static GLAccount withoutId(String code, Multilingual description) {
            return new GLAccount(null, code, description);
        }
    }

    public static PaymentLine withoutId(GLAccount glAccount, BigDecimal paidAmount, BigDecimal amount) {
        return new PaymentLine(
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
