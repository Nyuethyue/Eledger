package bhutan.eledger.domain.ref.taxperiod;

import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class OpenCloseTaxPeriodRecord {
    private final Long id;
    private final Integer periodId;
    private final String period;
    private final LocalDate periodStartDate;
    private final LocalDate periodEndDate;

    public static OpenCloseTaxPeriodRecord withoutId(
            Integer periodId,
            String period,
            LocalDate periodStartDate,
            LocalDate periodEndDate
    ) {
        return new OpenCloseTaxPeriodRecord(
                null,
                periodId,
                period,
                periodStartDate,
                periodEndDate
        );
    }
}
