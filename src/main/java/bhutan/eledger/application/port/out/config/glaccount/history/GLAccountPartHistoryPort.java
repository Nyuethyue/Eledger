package bhutan.eledger.application.port.out.config.glaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;

public interface GLAccountPartHistoryPort {

    HistoriesHolder<GLAccountPart> findRevisionsById(Long id);
}
