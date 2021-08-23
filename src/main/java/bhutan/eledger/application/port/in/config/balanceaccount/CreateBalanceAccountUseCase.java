package bhutan.eledger.application.port.in.config.balanceaccount;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreateBalanceAccountUseCase {

    Long create(@Valid CreateBalanceAccountCommand command);

    @Data
    class CreateBalanceAccountCommand {
        @NotEmpty
        @Valid
        private final Collection<Long> balanceAccountPartIds;
        @NotNull
        @Valid
        private final BalanceAccountLastPartCommand balanceAccountLastPart;
        @NotEmpty
        private final Map<String, String> descriptions;
    }


    @Data
    class BalanceAccountLastPartCommand {
        @NotNull
        private final String code;

        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
