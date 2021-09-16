package bhutan.eledger.application.port.out.config.balanceaccount.history;

import bhutan.eledger.common.history.Histories;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;

public interface BalanceAccountHistoryPort {

    Histories<BalanceAccount> findRevisionsById(Long id);
}
