package bhutan.eledger.domain.ref.nonworkingdays;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;
import java.time.MonthDay;

@Data(staticConstructor = "withId")
public class RefNonWorkingDays {
    private final Long id;
    private final String code;
    private final MonthDay startDay;
    private final MonthDay endDay;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Multilingual description;

    public static RefNonWorkingDays withoutId(
            String code,
            MonthDay startDay,
            MonthDay endDay,
            ValidityPeriod<LocalDate> validityPeriod,
            Multilingual description
    ) {
        return new RefNonWorkingDays(
                null,
                code,
                startDay,
                endDay,
                validityPeriod,
                description
        );
    }
}
