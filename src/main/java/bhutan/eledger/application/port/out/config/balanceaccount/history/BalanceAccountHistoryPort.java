package bhutan.eledger.application.port.out.config.balanceaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;

public interface BalanceAccountHistoryPort {

    HistoriesHolder<BalanceAccount> findRevisionsById(Long id);
}
