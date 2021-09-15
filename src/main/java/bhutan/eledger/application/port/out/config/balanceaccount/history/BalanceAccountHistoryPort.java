package bhutan.eledger.application.port.out.config.balanceaccount.history;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.history.Histories;

public interface BalanceAccountHistoryPort {

    Histories<BalanceAccount> findRevisionsById(Long id);
}
