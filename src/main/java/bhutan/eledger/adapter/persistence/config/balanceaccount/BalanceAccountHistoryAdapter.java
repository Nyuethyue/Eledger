package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.history.BalanceAccountHistoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.history.Histories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class BalanceAccountHistoryAdapter implements BalanceAccountHistoryPort {
    private final BalanceAccountHistoryMapper balanceAccountHistoryMapper;
    private final BalanceAccountEntityRepository balanceAccountEntityRepository;

    @Override
    public Histories<BalanceAccount> findRevisionsById(Long id) {
        var revisions = balanceAccountEntityRepository.findRevisions(id);

        return revisions.stream()
                .map(r ->
                        balanceAccountHistoryMapper.mapToDomain(r.getEntity(), r.getMetadata())
                )
                .collect(Collectors.collectingAndThen(
                                Collectors.toUnmodifiableList(),
                                Histories::of
                        )
                );
    }
}
