package bhutan.eledger.application.port.in.eledger.config.transaction;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
public interface CreateTransactionTypeUseCase {

    Long create(@Valid CreateTransactionTypeCommand command);

    @Data
    class CreateTransactionTypeCommand {
        @NotNull
        private final String code;

        @NotEmpty
        private final Map<String, String> descriptions;

    }
}
