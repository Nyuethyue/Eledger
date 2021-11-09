package bhutan.eledger.application.port.out.eledger.config.glaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;

public interface GLAccountPartHistoryPort {

    HistoriesHolder<GLAccountPart> findRevisionsById(Long id);
}
