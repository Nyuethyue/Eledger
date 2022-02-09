package bhutan.eledger.domain.ref.taxperiod;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data(staticConstructor = "withId")
public class RefOpenCloseTaxPeriod {
    private final Long    id;
    private final String  glAccountFullCode;
    private final Integer calendarYear;
    private final Long    taxPeriodTypeId;
    private final Long    transactionTypeId;
    private final Integer years;
    private final Integer month;
    private final Collection<RefOpenCloseTaxPeriodRecord> records;

    public static RefOpenCloseTaxPeriod withoutId(
            String glAccountFullCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Integer years,
            Integer month,
            Collection<RefOpenCloseTaxPeriodRecord> records
    ) {
        return new RefOpenCloseTaxPeriod(
                null,
                glAccountFullCode,
                calendarYear,
                taxPeriodTypeId,
                transactionTypeId,
                years,
                month,
                new ArrayList<>(records)
        );

    }

    public void upsertOpenCloseTaxPeriodRecord(RefOpenCloseTaxPeriodRecord refOpenCloseTaxPeriodRecord) {
        records.
                stream()
                .filter(record -> record.getPeriodId().equals(refOpenCloseTaxPeriodRecord.getPeriodId()))
                .findAny();
    }

}
