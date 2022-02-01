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
        private final Long taxTypeId;

        @Valid
        @NotNull
        @PositiveOrZero
        private Long calendarYear;

        @NotNull
        @NotEmpty
        private final Long taxPeriodTypeId;

        private final Long transactionTypeId;

        @Valid
        @NotNull
        @PositiveOrZero
        private Long dueDateCountForReturnFiling;

        @Valid
        @NotNull
        @PositiveOrZero
        private Long dueDateCountForPayment;

        @Valid
        @NotNull
        private LocalDate validFrom;

        @Valid
        @NotNull
        private LocalDate validTo;

        @Valid
        @NotNull
        private Boolean considerNonWorkingDays;


        @NotNull
        @NotEmpty
        @Valid
        private final Collection<TaxPeriodRecordCommand> records;
    }

    @Data
    class TaxPeriodRecordCommand {
        @Valid
        @NotNull
        private LocalDate periodStart;

        @Valid
        @NotNull
        private LocalDate periodEnd;

        @Valid
        @NotNull
        private LocalDate filingDueDate;

        @Valid
        @NotNull
        private LocalDate paymentDueDate;

        @Valid
        @NotNull
        private LocalDate interestCalcStartDate;


        @Valid
        @NotNull
        private LocalDate finePenaltyCalcStartDate;

        @Valid
        @NotNull
        private LocalDate validFrom;


        @Valid
        @NotNull
        private String remark;
    }
}
