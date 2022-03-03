package bhutan.eledger.application.port.in.eledger.accounting;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface CalculationNetNegativeBalanceUseCase {
    BigDecimal getNetNegativeBalance(CalculateBalanceCommand command);

    @Data
    class CalculateBalanceCommand {
        @NotNull
        @NotEmpty
        private final String tpn;
    }
}
