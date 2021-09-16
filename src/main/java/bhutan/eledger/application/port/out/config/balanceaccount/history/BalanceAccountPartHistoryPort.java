package bhutan.eledger.application.port.out.config.balanceaccount.history;

import bhutan.eledger.common.history.Histories;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;

public interface BalanceAccountPartHistoryPort {

    Histories<BalanceAccountPart> findRevisionsById(Long id);
}
