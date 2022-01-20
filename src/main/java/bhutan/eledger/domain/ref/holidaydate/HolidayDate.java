package bhutan.eledger.domain.ref.holidaydate;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class HolidayDate {
    private final Long id;
    private final String year;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Boolean isValidForOneYear;
    private final Multilingual description;

    public static HolidayDate withoutId(
            String year,
            ValidityPeriod<LocalDate> validityPeriod,
            Boolean isValidForOneYear,
            Multilingual description
    ) {
        return new HolidayDate(
                null,
                year,
                validityPeriod,
                isValidForOneYear,
                description
        );
    }
}
