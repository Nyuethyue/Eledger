package bhutan.eledger.application.port.in.config.balanceaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface GetBalanceAccountPartHistoryUseCase {

    HistoriesHolder<BalanceAccountPart> getHistoriesById(@NotNull Long balanceAccountId);
}
