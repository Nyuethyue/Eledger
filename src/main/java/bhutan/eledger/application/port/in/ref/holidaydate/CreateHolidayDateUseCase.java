package bhutan.eledger.application.port.in.ref.holidaydate;

import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreateHolidayDateUseCase {
    Collection<HolidayDate> create(@Valid CreateHolidayDateUseCase.CreateHolidayDateCommand command);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class CreateHolidayDateCommand {
        @NotNull
        @NotEmpty
        @Valid
        private Collection<HolidayDateCommand> holidayDates;
    }

    @Data
    class HolidayDateCommand {
        @NotNull
        private final String year;
        @NotNull
        private final LocalDate startOfValidity;
        @NotNull
        private final LocalDate endOfValidity;
        @NotNull
        private final Boolean isValidForOneYear;
        @NotEmpty
        private final Map<String, String> descriptions;
    }

}
