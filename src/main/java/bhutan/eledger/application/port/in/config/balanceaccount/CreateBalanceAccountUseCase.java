package bhutan.eledger.application.port.in.config.balanceaccount;

import lombok.Data;
import lombok.Getter;
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
        private final Map<String, String> descriptions;
        @NotEmpty
        @Valid
        private final Collection<BalanceAccountPartCommand> balanceAccountParts;
        @NotNull
        @Valid
        private final BalanceAccountLastPartCommand balanceAccountLastPart;
    }

    @Data
    class BalanceAccountPartCommand {
        private final Long parentId;
        @NotNull
        private final Integer balanceAccountPartTypeId;
        @NotNull
        private final String code;
    }


    @Getter
    class BalanceAccountLastPartCommand extends BalanceAccountPartCommand {
        @NotEmpty
        private final Map<String, String> descriptions;

        public BalanceAccountLastPartCommand(Long parentId, Integer balanceAccountPartTypeId, String code, Map<String, String> descriptions) {
            super(parentId, balanceAccountPartTypeId, code);
            this.descriptions = descriptions;
        }
    }
}
