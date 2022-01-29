package bhutan.eledger.domain.ref.holidaydate;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;
import java.time.MonthDay;

@Data(staticConstructor = "withId")
public class RefHolidayDate {
    private final Long id;
    private final String year;
    private final MonthDay startOfHoliday;
    private final MonthDay endOfHoliday;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Multilingual description;

    public static RefHolidayDate withoutId(
            String year,
            MonthDay startOfHoliday,
            MonthDay endOfHoliday,
            ValidityPeriod<LocalDate> validityPeriod,
            Multilingual description
    ) {
        return new RefHolidayDate(
                null,
                year,
                startOfHoliday,
                endOfHoliday,
                validityPeriod,
                description
        );
    }
}
