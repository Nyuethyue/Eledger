package bhutan.eledger.application.port.in.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface CreateCashMultiplePaymentsUseCase {

    Receipt create(@Valid CreateCashMultiplePaymentsCommand command);

    @ToString(callSuper = true)
    class CreateCashMultiplePaymentsCommand extends CreatePaymentsCommonCommand<CreateCashPaymentCommand> {

        public CreateCashMultiplePaymentsCommand(@NotNull Long refCurrencyId, @Valid @NotNull @NotEmpty Collection<CreateCashPaymentCommand> payments) {
            super(refCurrencyId, payments);
        }
    }

    @ToString(callSuper = true)
    class CreateCashPaymentCommand extends CreatePaymentCommonCommand {

        public CreateCashPaymentCommand(Long paymentAdviceId, Collection<PayableLineCommand> payments) {
            super(paymentAdviceId, payments);
        }
    }
}
