package bhutan.eledger.domain.ref.rrcocashcounters;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefRRCOCashCounters {
    private final Long id;
    private final String code;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Multilingual description;

    public static RefRRCOCashCounters withoutId(
            String code,
            ValidityPeriod<LocalDate> validityPeriod,
            Multilingual description
    ) {
        return new RefRRCOCashCounters(
                null,
                code,
                validityPeriod,
                description
        );
    }
}
