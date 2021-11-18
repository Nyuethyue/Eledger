package bhutan.eledger.application.port.in.ref.bank;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public interface CreateRefBankUseCase {

    Long create(@Valid CreateRefBankUseCase.CreateRefBankCommand command);

    @Data
    class CreateRefBankCommand {
        @NotNull
        @NotEmpty
        private final String code;

        @NotEmpty
        private final Map<String, String> descriptions;
    }

}
