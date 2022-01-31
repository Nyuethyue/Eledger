package bhutan.eledger.application.port.in.ref.nonworkingdays;

import am.iunetworks.lib.common.validation.constraints.CompareFields;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
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
public interface CreateRefNonWorkingDaysUseCase {
    Collection<RefNonWorkingDays> create(@Valid CreateRefNonWorkingDaysUseCase.CreateRefNonWorkingDaysCommand command);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class CreateRefNonWorkingDaysCommand {
        @NotNull
        @NotEmpty
        @Valid
        private Collection<RefNonWorkingDayCommand> nonWorkingDays;
    }

    @Data
    @AllArgsConstructor(onConstructor = @__(@JsonCreator))
    @CompareFields(type = RefNonWorkingDayCommand.class, leftField = "startDay", operator = "<=", rightField = "endDay")
    class RefNonWorkingDayCommand {
        @NotNull
        private final Integer year;
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM")
        private final MonthDay startDay;
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM")
        private final MonthDay endDay;
        @NotNull
        private final LocalDate startOfValidity;
        private final LocalDate endOfValidity;
        @NotEmpty
        private final Map<String, String> descriptions;
    }

}
