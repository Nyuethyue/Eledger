package bhutan.eledger.domain.ref.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefOpenCloseTaxPeriodRecord {
    private final Long id;
    private final Long periodSegmentId;
    private final Multilingual periodSegmentName;
    private final LocalDate periodOpenDate;
    private final LocalDate periodCloseDate;

    public static RefOpenCloseTaxPeriodRecord withoutId(
            Long periodSegmentId,
            Multilingual periodSegmentName,
            LocalDate periodOpenDate,
            LocalDate periodCloseDate
    ) {
        return new RefOpenCloseTaxPeriodRecord(
                null,
                periodSegmentId,
                periodSegmentName,
                periodOpenDate,
                periodCloseDate
        );
    }
}
