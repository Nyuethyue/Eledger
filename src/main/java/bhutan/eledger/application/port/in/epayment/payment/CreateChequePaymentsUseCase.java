package bhutan.eledger.application.port.in.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface CreateChequePaymentsUseCase {

    Receipt create(@Valid CreateChequePaymentsUseCase.CreateChequePaymentsCommand command);

    @Getter
    @ToString(callSuper = true)
    class CreateChequePaymentsCommand extends CreatePaymentsCommonCommand<CreateChequePaymentCommand> {
        @NotNull
        private final Long bankBranchId;
        @NotNull
        private final String instrumentNumber;
        @NotNull
        private final LocalDate instrumentDate;
        private final String otherReferenceNumber;

        public CreateChequePaymentsCommand(Long refCurrencyId, Collection<CreateChequePaymentCommand> receipts, Long bankBranchId, String instrumentNumber, LocalDate instrumentDate, String otherReferenceNumber) {
            super(refCurrencyId, receipts);
            this.bankBranchId = bankBranchId;
            this.instrumentNumber = instrumentNumber;
            this.instrumentDate = instrumentDate;
            this.otherReferenceNumber = otherReferenceNumber;
        }
    }

    @Getter
    @ToString(callSuper = true)
    class CreateChequePaymentCommand extends CreatePaymentCommonCommand {

        public CreateChequePaymentCommand(@NotNull Long paymentAdviceId, @NotNull @NotEmpty Collection<PaymentCommand> payments) {
            super(paymentAdviceId, payments);
        }
    }
}
