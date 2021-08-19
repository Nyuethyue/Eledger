package bhutan.eledger.application.port.in.config.balanceaccount;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

@Validated
public interface CreateBalanceAccountPartTypeUseCase {

    Integer create(@Valid CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand command);

    @Data
    class CreateBalanceAccountPartTypeCommand {
        @NotNull
        @Positive
        private final Integer level;
        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
