package bhutan.eledger.application.service.eledger.glaccount.history;

import bhutan.eledger.application.port.in.eledger.config.glaccount.history.GetGLAccountPartHistoryUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.history.GLAccountPartHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class GetGLAccountHistoryPartService implements GetGLAccountPartHistoryUseCase {

    private final GLAccountPartHistoryPort glAccountPartHistoryPort;

    @Override
    public HistoriesHolder<GLAccountPart> getHistoriesById(Long glAccountId) {

        return glAccountPartHistoryPort.findRevisionsById(glAccountId);

    }
}
