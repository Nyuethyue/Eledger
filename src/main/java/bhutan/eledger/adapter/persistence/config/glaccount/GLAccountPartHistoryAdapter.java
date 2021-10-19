package bhutan.eledger.adapter.persistence.config.glaccount;

import bhutan.eledger.application.port.out.config.glaccount.history.GLAccountPartHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.common.history.persistence.envers.mapper.HistoryMapper;
import bhutan.eledger.common.history.persistence.envers.mapper.HistoryMetadataMapper;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GLAccountPartHistoryAdapter implements GLAccountPartHistoryPort {
    private final GLAccountPartMapper glAccountPartMapper;
    private final HistoryMetadataMapper historyMetadataMapper;
    private final GLAccountPartEntityRepository glAccountPartEntityRepository;
    private final HistoryMapper historyMapper;

    @Override
    public HistoriesHolder<GLAccountPart> findRevisionsById(Long id) {
        var revisions = glAccountPartEntityRepository.findRevisions(id);

        return historyMapper.mapToHistoriesHolder(revisions, glAccountPartMapper::mapToDomain);
    }
}
