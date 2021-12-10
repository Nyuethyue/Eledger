package bhutan.eledger.application.port.in.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface CreateChequePaymentUseCase {

    Receipt create(@Valid CreateChequePaymentCommand command);

    @Getter
    @ToString(callSuper = true)
    class CreateChequePaymentCommand extends CreatePaymentCommonCommand {
        @NotNull
        private final Long bankBranchId;
        @NotNull
        private final String instrumentNumber;
        @NotNull
        private final LocalDate instrumentDate;
        private final String otherReferenceNumber;

        public CreateChequePaymentCommand(Long paymentAdviceId, Long refCurrencyId, Collection<PaymentCommand> payments, Long bankBranchId, String instrumentNumber, LocalDate instrumentDate, String otherReferenceNumber) {
            super(paymentAdviceId, refCurrencyId, payments);
            this.bankBranchId = bankBranchId;
            this.instrumentNumber = instrumentNumber;
            this.instrumentDate = instrumentDate;
            this.otherReferenceNumber = otherReferenceNumber;
        }
    }
}
