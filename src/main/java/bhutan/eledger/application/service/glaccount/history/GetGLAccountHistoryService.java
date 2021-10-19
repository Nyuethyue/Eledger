package bhutan.eledger.application.service.glaccount.history;

import bhutan.eledger.application.port.in.config.glaccount.history.GetGLAccountHistoryUseCase;
import bhutan.eledger.application.port.out.config.glaccount.history.GLAccountHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class GetGLAccountHistoryService implements GetGLAccountHistoryUseCase {

    private final GLAccountHistoryPort glAccountHistoryPort;

    @Override
    public HistoriesHolder<GLAccount> getHistoriesById(Long glAccountId) {

        return glAccountHistoryPort.findRevisionsById(glAccountId);

    }
}
