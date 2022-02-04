package bhutan.eledger.domain.ref.taxperiod;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data(staticConstructor = "withId")
public class RefOpenCloseTaxPeriodConfig {
    private final Long id;
    private final String taxTypeCode;
    private final int calendarYear;
    private final Long taxPeriodTypeId;
    private final Long transactionTypeId;
    private final int years;
    private final int month;
    private final Collection<OpenCloseTaxPeriodRecord> records;

    public static RefOpenCloseTaxPeriodConfig withoutId(
            String taxTypeCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Integer years,
            Integer month,
            Collection<OpenCloseTaxPeriodRecord> records
    ) {
        return new RefOpenCloseTaxPeriodConfig(
                null,
                taxTypeCode,
                calendarYear,
                taxPeriodTypeId,
                transactionTypeId,
                years,
                month,
                new ArrayList<>(records)
        );

    }
}
