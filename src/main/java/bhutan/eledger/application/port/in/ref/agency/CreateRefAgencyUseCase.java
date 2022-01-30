package bhutan.eledger.application.port.in.ref.agency;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Validated
public interface CreateRefAgencyUseCase {

    Long create(@Valid CreateRefAgencyUseCase.CreateRefAgencyCommand command);

    @Data
    class CreateRefAgencyCommand {
        @NotNull
        @NotEmpty
        private final String code;

        @NotNull
        private final LocalDate startOfValidity;

        private final LocalDate endOfValidity;

        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
