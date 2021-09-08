package bhutan.eledger.application.port.in.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@Validated
public interface UpdateBalanceAccountUseCase {

    void updateBalanceAccount(@NotNull Long id, @Valid UpdateBalanceAccountCommand command);

    @Data
    class UpdateBalanceAccountCommand {
        @NotNull
        @NotEmpty
        private final Map<String, String> descriptions;
        @NotNull
        private final BalanceAccountStatus balanceAccountStatus;
        @FutureOrPresent
        private final LocalDateTime actualDate;
    }
}
