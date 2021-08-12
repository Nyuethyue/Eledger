package bhutan.eledger.application.port.in.config.balanceaccount;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public interface CreateBalanceAccountPartLevelUseCase {

    Long create(CreateBalanceAccountPartLevelCommand command);

    @Data
    class CreateBalanceAccountPartLevelCommand {
        @NotNull
        private final Integer level;
        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
