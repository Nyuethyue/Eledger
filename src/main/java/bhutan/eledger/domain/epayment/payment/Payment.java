package bhutan.eledger.domain.epayment.payment;

import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import lombok.Data;

import java.math.BigDecimal;

@Data(staticConstructor = "withId")
public class Payment {

    private final Long id;
    private final EpGLAccount glAccount;
    private final BigDecimal paidAmount;
    private final Long payableLineId;
    private final Long elTargetTransactionId;
    private final PaymentPaInfo paymentAdviceInfo;

    public static Payment withoutId(
            EpGLAccount glAccount,
            BigDecimal paidAmount,
            Long payableLineId,
            Long elTargetTransactionId,
            PaymentPaInfo paymentAdviceInfo
    ) {
        return new Payment(
                null,
                glAccount,
                paidAmount,
                payableLineId,
                elTargetTransactionId,
                paymentAdviceInfo
        );
    }
}
