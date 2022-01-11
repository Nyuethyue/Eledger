package bhutan.eledger.application.port.in.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface CreatePosPaymentsUseCase {
    Receipt create(@Valid CreatePosPaymentsUseCase.CreatePosPaymentsCommand command);

    @Getter
    @ToString(callSuper = true)
    class CreatePosPaymentsCommand extends CreatePaymentsCommonCommand<CreatePosPaymentsUseCase.CreatePosPaymentCommand> {
        @NotNull
        private final String posReferenceNumber;

        public CreatePosPaymentsCommand(Long refCurrencyId, Collection<CreatePosPaymentsUseCase.CreatePosPaymentCommand> payments, String posReferenceNumber) {
            super(refCurrencyId, payments);
            this.posReferenceNumber = posReferenceNumber;
        }
    }

    @Getter
    @ToString(callSuper = true)
    class CreatePosPaymentCommand extends CreatePaymentCommonCommand {

        public CreatePosPaymentCommand(@NotNull Long paymentAdviceId, @NotNull @NotEmpty Collection<PayableLineCommand> payments) {
            super(paymentAdviceId, payments);
        }
    }
}
