package bhutan.eledger.domain.ref.taxperiod;

import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefOpenCloseTaxPeriodRecord {
    private final Long id;
    private final Integer periodId;
    private final String period;
    private final LocalDate periodOpenDate;
    private final LocalDate periodCloseDate;

    public static RefOpenCloseTaxPeriodRecord withoutId(
            Integer periodId,
            String period,
            LocalDate periodOpenDate,
            LocalDate periodCloseDate
    ) {
        return new RefOpenCloseTaxPeriodRecord(
                null,
                periodId,
                period,
                periodOpenDate,
                periodCloseDate
        );
    }
}
