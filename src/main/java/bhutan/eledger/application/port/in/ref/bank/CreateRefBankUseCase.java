package bhutan.eledger.application.port.in.ref.bank;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
@Validated
public interface CreateRefBankUseCase {

    Long create(@Valid CreateRefBankUseCase.CreateRefBankCommand command);

    @Data
    class CreateRefBankCommand {
        @NotNull
        @NotEmpty
        private final String code;

        @NotNull
        private final LocalDate startOfValidity;

        @NotEmpty
        private final Map<String, String> descriptions;
    }

}
