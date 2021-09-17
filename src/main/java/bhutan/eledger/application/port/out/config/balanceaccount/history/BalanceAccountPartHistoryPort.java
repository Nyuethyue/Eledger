package bhutan.eledger.application.port.out.config.balanceaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;

public interface BalanceAccountPartHistoryPort {

    HistoriesHolder<BalanceAccountPart> findRevisionsById(Long id);
}
