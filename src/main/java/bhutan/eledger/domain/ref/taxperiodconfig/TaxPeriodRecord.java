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
    private final LocalDate interestCalcStartDay;

    @NotNull
    @NotEmpty
    private final LocalDate fineAndPenaltyCalcStartDay;

    @Valid
    @NotNull
    private LocalDate validFrom;

    @Valid
    @NotNull
    private String remark;

    public static TaxPeriodRecord withId(
            Long id,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDay,
            LocalDate fineAndPenaltyCalcStartDay,
            LocalDate validFrom,
            String remark
    ) {
        return new TaxPeriodRecord(
                id,
                periodStartDate,
                periodEndDate,
                filingDueDate,
                paymentDueDate,
                interestCalcStartDay,
                fineAndPenaltyCalcStartDay,
                validFrom,
                remark
        );
    }

    public static TaxPeriodRecord withoutId(
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDay,
            LocalDate fineAndPenaltyCalcStartDay,
            LocalDate validFrom,
            String remark
    ) {
        return new TaxPeriodRecord(
                null,
                periodStartDate,
                periodEndDate,
                filingDueDate,
                paymentDueDate,
                interestCalcStartDay,
                fineAndPenaltyCalcStartDay,
                validFrom,
                remark
        );
    }
}
