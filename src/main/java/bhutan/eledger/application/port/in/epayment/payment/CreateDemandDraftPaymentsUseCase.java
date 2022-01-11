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
public interface CreateDemandDraftPaymentsUseCase {
    Receipt create(@Valid CreateDemandDraftPaymentsUseCase.CreateDemandDraftPaymentsCommand command);

    @Getter
    @ToString(callSuper = true)
    class CreateDemandDraftPaymentsCommand extends CreatePaymentsCommonCommand<CreateDemandDraftPaymentsUseCase.CreateDemandDraftPaymentCommand> {
        @NotNull
        private final Long payableBankBranchId;
        @NotNull
        private final Long issuingBankBranchId;
        @NotNull
        private final String instrumentNumber;
        @NotNull
        private final LocalDate instrumentDate;
        private final String otherReferenceNumber;

        public CreateDemandDraftPaymentsCommand(Long refCurrencyId, Collection<CreateDemandDraftPaymentsUseCase.CreateDemandDraftPaymentCommand> payments, Long payableBankBranchId, Long issuingBankBranchId, String instrumentNumber, LocalDate instrumentDate, String otherReferenceNumber) {
            super(refCurrencyId, payments);
            this.payableBankBranchId = payableBankBranchId;
            this.issuingBankBranchId = issuingBankBranchId;
            this.instrumentNumber = instrumentNumber;
            this.instrumentDate = instrumentDate;
            this.otherReferenceNumber = otherReferenceNumber;
        }
    }

    @Getter
    @ToString(callSuper = true)
    class CreateDemandDraftPaymentCommand extends CreatePaymentCommonCommand {

        public CreateDemandDraftPaymentCommand(@NotNull Long paymentAdviceId, @NotNull @NotEmpty Collection<PayableLineCommand> payments) {
            super(paymentAdviceId, payments);
        }
    }
}
