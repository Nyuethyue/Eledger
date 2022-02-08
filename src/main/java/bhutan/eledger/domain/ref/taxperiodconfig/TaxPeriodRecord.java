package bhutan.eledger.domain.ref.taxperiodconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@ToString
public class TaxPeriodRecord {
    private final Long id;

    @NotNull
    private final Integer periodId;

    @NotNull
    private final String periodName;

    @NotNull
    @NotEmpty
    private final LocalDate periodStartDate;

    @NotNull
    @NotEmpty
    private final LocalDate periodEndDate;

    @NotNull
    @NotEmpty
    private final LocalDate filingDueDate;

    @NotNull
    @NotEmpty
    private final LocalDate paymentDueDate;

    @NotNull
    @NotEmpty
    private final LocalDate interestCalcStartDate;

    @NotNull
    @NotEmpty
    private final LocalDate fineAndPenaltyCalcStartDate;

    @Valid
    @NotNull
    private LocalDate validFrom;

    @Valid
    @NotNull
    private String taxTypeCode;

    public static TaxPeriodRecord withId(
            Long id,
            Integer periodId,
            String periodName,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDate,
            LocalDate fineAndPenaltyCalcStartDate,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        return new TaxPeriodRecord(
                id,
                periodId,
                periodName,
                periodStartDate,
                periodEndDate,
                filingDueDate,
                paymentDueDate,
                interestCalcStartDate,
                fineAndPenaltyCalcStartDate,
                validFrom,
                taxTypeCode
        );
    }

    public static TaxPeriodRecord withoutId(
            Integer periodId,
            String periodName,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDate,
            LocalDate fineAndPenaltyCalcStartDate,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        return new TaxPeriodRecord(
                null,
                periodId,
                periodName,
                periodStartDate,
                periodEndDate,
                filingDueDate,
                paymentDueDate,
                interestCalcStartDate,
                fineAndPenaltyCalcStartDate,
                validFrom,
                taxTypeCode
        );
    }
}
