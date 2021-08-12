package bhutan.eledger.application.port.in.config.balanceaccount;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface CreateBalanceAccountUseCase {

    Long create(CreateBalanceAccountCommand command);

    @Data
    class CreateBalanceAccountCommand {
        private final Integer parentId;
        @NotNull
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        @NotEmpty
        private final Map<String, String> descriptions;
        @NotEmpty
        private final Collection<BalanceAccountPartCommand> balanceAccountParts;
        @NotNull
        private final BalanceAccountLastPartCommand BalanceAccountLastPart;
    }

    @Data
    class BalanceAccountPartCommand {
        private final Integer level;
        private final String code;
    }

    @Data
    class BalanceAccountLastPartCommand {
        @NotNull
        private final Integer level;
        @NotNull
        private final String code;
        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
