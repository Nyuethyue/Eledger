package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface UpsertTaxPeriodUseCase {

    Long upsert(@Valid UpsertTaxPeriodCommand command);

    @Data
    class UpsertTaxPeriodCommand {
        private final Long id;

        @NotNull
        @NotEmpty
        private final String taxTypeCode;

        @Valid
        @NotNull
        @PositiveOrZero
        private final Integer calendarYear;

        @NotNull
        private final String taxPeriodCode;

        @NotNull
        private final Long transactionTypeId;

        @Valid
        @NotNull
        @PositiveOrZero
        private final Integer dueDateCountForReturnFiling;

        @Valid
        @NotNull
        @PositiveOrZero
        private final Integer dueDateCountForPayment;

        @Valid
        @NotNull
        private final LocalDate validFrom;

        @Valid
        @NotNull
        private final LocalDate validTo;

        @Valid
        @NotNull
        private final Boolean considerNonWorkingDays;


        @NotNull
        @NotEmpty
        @Valid
        private final Collection<TaxPeriodRecordCommand> records;
    }

    @Data
    class TaxPeriodRecordCommand {
        @Valid
        @NotNull
        private final Long periodSegmentId;

        @Valid
        @NotNull
        private final LocalDate periodStart;

        @Valid
        @NotNull
        private final LocalDate periodEnd;

        @Valid
        @NotNull
        private final LocalDate filingDueDate;

        @Valid
        @NotNull
        private final LocalDate paymentDueDate;

        @Valid
        @NotNull
        private final LocalDate interestCalcStartDate;

        @Valid
        @NotNull
        private final LocalDate finePenaltyCalcStartDate;

        @Valid
        @NotNull
        private final LocalDate validFrom;

        @Valid
        @NotNull
        private final String taxTypeCode;
    }
}
