package bhutan.eledger.domain.ref.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
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
public class TaxPeriodConfigRecord {
    private final Long id;

    @NotNull
    private final Long periodId;

    private final String periodCode;

    @NotNull
    private final Multilingual periodName;

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

    public static TaxPeriodConfigRecord withId(
            Long id,
            Long periodId,
            String periodCode,
            Multilingual periodSegmentName,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDate,
            LocalDate fineAndPenaltyCalcStartDate,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        return new TaxPeriodConfigRecord(
                id,
                periodId,
                periodCode,
                periodSegmentName,
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

    public static TaxPeriodConfigRecord withoutId(
            Long periodSegmentId,
            String periodCode,
            Multilingual periodSegmentName,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDate,
            LocalDate fineAndPenaltyCalcStartDate,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        return new TaxPeriodConfigRecord(
                null,
                periodSegmentId,
                periodCode,
                periodSegmentName,
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
