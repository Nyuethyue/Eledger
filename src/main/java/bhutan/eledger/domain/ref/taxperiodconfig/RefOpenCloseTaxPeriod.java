package bhutan.eledger.domain.ref.taxperiodconfig;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data(staticConstructor = "withId")
public class RefOpenCloseTaxPeriod {
    private final Long id;
    private final String glAccountPartFullCode;
    private final Integer calendarYear;
    private final String taxPeriodCode;
    private final Long transactionTypeId;
    private final Integer noOfYears;
    private final Integer noOfMonth;
    private final Collection<RefOpenCloseTaxPeriodRecord> records;

    public static RefOpenCloseTaxPeriod withoutId(
            String glAccountPartFullCode,
            Integer calendarYear,
            String taxPeriodCode,
            Long transactionTypeId,
            Integer noOfYears,
            Integer noOfMonth,
            Collection<RefOpenCloseTaxPeriodRecord> records
    ) {
        return new RefOpenCloseTaxPeriod(
                null,
                glAccountPartFullCode,
                calendarYear,
                taxPeriodCode,
                transactionTypeId,
                noOfYears,
                noOfMonth,
                new ArrayList<>(records)
        );

    }


}
