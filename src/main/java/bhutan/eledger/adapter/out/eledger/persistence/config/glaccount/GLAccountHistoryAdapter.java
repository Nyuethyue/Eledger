package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import bhutan.eledger.application.port.out.eledger.config.glaccount.history.GLAccountHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.common.history.persistence.envers.mapper.HistoryMapper;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GLAccountHistoryAdapter implements GLAccountHistoryPort {
    private final GLAccountMapper glAccountMapper;
    private final GLAccountEntityRepository glAccountEntityRepository;
    private final HistoryMapper historyMapper;

    @Override
    public HistoriesHolder<GLAccount> findRevisionsById(Long id) {
        var revisions = glAccountEntityRepository.findRevisions(id);

        return historyMapper.mapToHistoriesHolder(revisions, glAccountMapper::mapToDomain);
    }
}
