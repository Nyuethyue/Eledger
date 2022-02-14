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
    @PositiveOrZero
    private int calendarYear;

    @NotNull
    private final String taxPeriodCode;

    private final Long transactionTypeId;

    @Valid
    @PositiveOrZero
    private int dueDateCountForReturnFiling;

    @Valid
    @PositiveOrZero
    private int dueDateCountForPayment;

    @Valid
    @NotNull
    private LocalDate validFrom;

    private LocalDate validTo;

    @Valid
    @NotNull
    private Boolean considerNonWorkingDays;


    @NotNull
    @NotEmpty
    @Valid
    private final Collection<TaxPeriodConfigRecord> records;


    public static RefTaxPeriodConfig withId(
            Long id,
            String taxTypeCode,
            Integer calendarYear,
            String taxPeriodCode,
            Long transactionTypeId,
            Integer dueDateCountForReturnFiling,
            Integer dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays,
            Collection<TaxPeriodConfigRecord> records
    ) {
        return new RefTaxPeriodConfig(
                id,
                taxTypeCode,
                calendarYear,
                taxPeriodCode,
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
            String taxPeriodCode,
            Long transactionTypeId,
            Integer dueDateCountForReturnFiling,
            Integer dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays,
            Collection<TaxPeriodConfigRecord> records
    ) {
        return new RefTaxPeriodConfig(
                null,
                taxTypeCode,
                calendarYear,
                taxPeriodCode,
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
