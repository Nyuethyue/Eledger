package bhutan.eledger.application.port.in.ref.currency;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
public interface CreateRefCurrencyUseCase {

    Long create(@Valid CreateRefCurrencyUseCase.CreateCurrencyCommand command);

    @Data
    class CreateCurrencyCommand {
        @NotNull
        @NotEmpty
        private final String code;
        @NotNull
        @NotEmpty
        private final String symbol;
        @NotEmpty
        private final Map<String, String> description;
    }

}
