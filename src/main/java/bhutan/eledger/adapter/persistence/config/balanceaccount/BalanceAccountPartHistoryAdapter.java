package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.history.BalanceAccountPartHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.common.history.persistence.envers.mapper.HistoryMapper;
import bhutan.eledger.common.history.persistence.envers.mapper.HistoryMetadataMapper;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BalanceAccountPartHistoryAdapter implements BalanceAccountPartHistoryPort {
    private final BalanceAccountPartMapper balanceAccountPartMapper;
    private final HistoryMetadataMapper historyMetadataMapper;
    private final BalanceAccountPartEntityRepository balanceAccountPartEntityRepository;
    private final HistoryMapper historyMapper;

    @Override
    public HistoriesHolder<BalanceAccountPart> findRevisionsById(Long id) {
        var revisions = balanceAccountPartEntityRepository.findRevisions(id);

        return historyMapper.mapToHistoriesHolder(revisions, balanceAccountPartMapper::mapToDomain);
    }
}
