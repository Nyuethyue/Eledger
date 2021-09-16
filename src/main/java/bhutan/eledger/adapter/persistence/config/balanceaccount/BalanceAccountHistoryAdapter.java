package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.history.BalanceAccountHistoryPort;
import bhutan.eledger.common.history.Histories;
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
    public Histories<BalanceAccount> findRevisionsById(Long id) {
        var revisions = balanceAccountEntityRepository.findRevisions(id);

        return historyMapper.map(revisions, balanceAccountMapper::mapToDomain);
    }
}
