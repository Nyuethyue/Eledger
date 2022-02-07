package bhutan.eledger.domain.ref.taxperiodconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.MonthDay;

@Builder(toBuilder = true)
@Getter
@ToString
public class TaxPeriodRecord {
    private final Long id;

    private final Integer periodId;

    private final int year;

    @NotNull
    @NotEmpty
    private final MonthDay periodStartDate;

    @NotNull
    @NotEmpty
    private final MonthDay periodEndDate;

    @NotNull
    @NotEmpty
    private final MonthDay filingDueDate;

    @NotNull
    @NotEmpty
    private final MonthDay paymentDueDate;

    @NotNull
    @NotEmpty
    private final MonthDay interestCalcStartDay;

    @NotNull
    @NotEmpty
    private final MonthDay fineAndPenaltyCalcStartDay;

    @Valid
    @NotNull
    private LocalDate validFrom;

    @Valid
    @NotNull
    private String taxTypeCode;

    public static TaxPeriodRecord withId(
            Long id,
            Integer periodId,
            int year,
            MonthDay periodStartDate,
            MonthDay periodEndDate,
            MonthDay filingDueDate,
            MonthDay paymentDueDate,
            MonthDay interestCalcStartDay,
            MonthDay fineAndPenaltyCalcStartDay,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        return new TaxPeriodRecord(
                id,
                periodId,
                year,
                periodStartDate,
                periodEndDate,
                filingDueDate,
                paymentDueDate,
                interestCalcStartDay,
                fineAndPenaltyCalcStartDay,
                validFrom,
                taxTypeCode
        );
    }

    public static TaxPeriodRecord withoutId(
            Integer periodId,
            int year,
            MonthDay periodStartDate,
            MonthDay periodEndDate,
            MonthDay filingDueDate,
            MonthDay paymentDueDate,
            MonthDay interestCalcStartDay,
            MonthDay fineAndPenaltyCalcStartDay,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        return new TaxPeriodRecord(
                null,
                periodId,
                year,
                periodStartDate,
                periodEndDate,
                filingDueDate,
                paymentDueDate,
                interestCalcStartDay,
                fineAndPenaltyCalcStartDay,
                validFrom,
                taxTypeCode
        );
    }
}
