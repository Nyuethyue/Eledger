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
    private final Integer noOfYears;
    private final Integer noOfMonth;
    private final Collection<RefOpenCloseTaxPeriodRecord> records;

    public static RefOpenCloseTaxPeriod withoutId(
            String glAccountFullCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Integer noOfYears,
            Integer noOfMonth,
            Collection<RefOpenCloseTaxPeriodRecord> records
    ) {
        return new RefOpenCloseTaxPeriod(
                null,
                glAccountFullCode,
                calendarYear,
                taxPeriodTypeId,
                transactionTypeId,
                noOfYears,
                noOfMonth,
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
