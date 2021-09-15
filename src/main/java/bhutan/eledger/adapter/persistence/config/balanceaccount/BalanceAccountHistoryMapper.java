package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.adapter.persistence.audit.AuditRevisionEntity;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.history.History;
import bhutan.eledger.domain.config.balanceaccount.history.HistoryMetadata;
import bhutan.eledger.domain.config.balanceaccount.history.HistoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
class BalanceAccountHistoryMapper {
    private final BalanceAccountMapper balanceAccountMapper;

    History<BalanceAccount> mapToDomain(BalanceAccountEntity balanceAccountEntity, RevisionMetadata<Long> metadata) {
        AuditRevisionEntity revision = metadata.getDelegate();

        return History.of(
                balanceAccountMapper.mapToDomain(balanceAccountEntity),
                HistoryMetadata.of(
                        revision.getId(),
                        Instant.ofEpochMilli(revision.getTimestamp()),
                        revision.getUsername(),
                        HistoryType.fromRevisionType(metadata.getRevisionType().name())
                )
        );
    }
}
