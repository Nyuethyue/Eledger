package bhutan.eledger.application.port.out.config.glaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.glaccount.GLAccount;

public interface GLAccountHistoryPort {

    HistoriesHolder<GLAccount> findRevisionsById(Long id);
}
