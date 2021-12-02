package bhutan.eledger.application.port.in.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface CreateCashPaymentUseCase {

    Receipt create(@Valid CreateCashPaymentCommand command);

    class CreateCashPaymentCommand extends CreatePaymentCommonCommand {

        public CreateCashPaymentCommand(Long paymentAdviceId, Long refCurrencyId, Collection<PaymentCommand> payments) {
            super(paymentAdviceId, refCurrencyId, payments);
        }
    }
}
