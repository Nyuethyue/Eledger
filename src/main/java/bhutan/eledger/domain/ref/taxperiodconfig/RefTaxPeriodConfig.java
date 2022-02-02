package bhutan.eledger.domain.ref.taxperiodconfig;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Builder(toBuilder = true)
@Getter
@ToString
public class RefTaxPeriodConfig {
    private final Long id;

    @NotNull
    @NotEmpty
    private final String taxTypeCode;

    @Valid
    @NotNull
    @PositiveOrZero
    private int calendarYear;

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
    private final Collection<TaxPeriodRecord> records;


    public static RefTaxPeriodConfig withId(
            Long id,
            String taxTypeCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Long dueDateCountForReturnFiling,
            Long dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays,
            Collection<TaxPeriodRecord> records
    ) {
        return new RefTaxPeriodConfig(
                id,
                taxTypeCode,
                calendarYear,
                taxPeriodTypeId,
                transactionTypeId,
                dueDateCountForReturnFiling,
                dueDateCountForPayment,
                validFrom,
                validTo,
                considerNonWorkingDays,
                new ArrayList<>(records)
        );
    }

    public static RefTaxPeriodConfig withoutId(
            String taxTypeCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Long dueDateCountForReturnFiling,
            Long dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays,
            Collection<TaxPeriodRecord> records
    ) {
        return new RefTaxPeriodConfig(
                null,
                taxTypeCode,
                calendarYear,
                taxPeriodTypeId,
                transactionTypeId,
                dueDateCountForReturnFiling,
                dueDateCountForPayment,
                validFrom,
                validTo,
                considerNonWorkingDays,
                new ArrayList<>(records)
        );

    }
}
