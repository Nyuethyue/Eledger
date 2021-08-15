package bhutan.eledger.application.port.in.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Map;

public interface CreateBalanceAccountPartUseCase {

    Collection<BalanceAccountPart> create(CreateBalanceAccountPartCommand command);

    @Data
    class CreateBalanceAccountPartCommand {
        private final Integer parentId;
        @NotNull
        @Positive
        private final Integer balanceAccountPartLevelId;

        @NotNull
        @Valid
        private final Collection<BalanceAccountPartCommand> balanceAccountParts;
    }

    @Data
    class BalanceAccountPartCommand {
        @NotNull
        private final String code;
        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
