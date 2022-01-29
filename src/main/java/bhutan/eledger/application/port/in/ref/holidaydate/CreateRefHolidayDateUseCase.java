package bhutan.eledger.application.port.in.ref.holidaydate;

import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreateRefHolidayDateUseCase {
    Collection<RefHolidayDate> create(@Valid CreateRefHolidayDateUseCase.CreateRefHolidayDateCommand command);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class CreateRefHolidayDateCommand {
        @NotNull
        @NotEmpty
        @Valid
        private Collection<RefHolidayDateCommand> holidayDates;
    }

    @Data
    @AllArgsConstructor(onConstructor = @__(@JsonCreator))
    class RefHolidayDateCommand {
        @NotNull
        private final String year;
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM")
        private final MonthDay startOfHoliday;
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM")
        private final MonthDay endOfHoliday;
        @NotNull
        private final LocalDate startOfValidity;
        @NotNull
        private final LocalDate endOfValidity;
        @NotEmpty
        private final Map<String, String> descriptions;
    }

}
