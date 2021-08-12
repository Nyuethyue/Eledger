package bhutan.eledger.application.port.in.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

public interface CreateBalanceAccountPartUseCase {

    BalanceAccountPart create(CreateBalanceAccountPartCommand command);

    @Data
    class CreateBalanceAccountPartCommand {
        private final Integer parentId;
        @NotNull
        private final Integer level;
        @NotNull
        private final String code;
        @NotNull
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
