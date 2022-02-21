package bhutan.eledger.application.port.in.epayment.payment.rma;

import lombok.Data;

import java.math.BigDecimal;

public interface CompleteRmaPaymentUseCase {

    void complete(CompleteRmaPaymentCommand command);

    @Data
    class CompleteRmaPaymentCommand {
        private final Long paymentAdviceId;
        private final BigDecimal paidAmount;
    }
}
