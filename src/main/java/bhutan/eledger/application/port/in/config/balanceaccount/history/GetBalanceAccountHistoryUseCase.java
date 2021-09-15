package bhutan.eledger.application.port.in.config.balanceaccount.history;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.history.Histories;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface GetBalanceAccountHistoryUseCase {

    Histories<BalanceAccount> getHistory(@NotNull Long balanceAccountId);
}
