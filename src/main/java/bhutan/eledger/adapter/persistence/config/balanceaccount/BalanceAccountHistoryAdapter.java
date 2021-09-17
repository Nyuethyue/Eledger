package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.history.BalanceAccountHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.common.history.persistence.envers.mapper.HistoryMapper;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BalanceAccountHistoryAdapter implements BalanceAccountHistoryPort {
    private final BalanceAccountMapper balanceAccountMapper;
    private final BalanceAccountEntityRepository balanceAccountEntityRepository;
    private final HistoryMapper historyMapper;

    @Override
    public HistoriesHolder<BalanceAccount> findRevisionsById(Long id) {
        var revisions = balanceAccountEntityRepository.findRevisions(id);

        return historyMapper.mapToHistoriesHolder(revisions, balanceAccountMapper::mapToDomain);
    }
}
