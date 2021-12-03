package bhutan.eledger.adapter.out.persistence.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.epayment.deposit.DepositSearchPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
class DepositSearchAdapter implements DepositSearchPort {
    private final DepositEntityRepository receiptEntityRepository;
    private final DepositMapper depositMapper;

    @Override
    public SearchResult<Deposit> search(DepositCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<Deposit> page = receiptEntityRepository.findAll(
                        querydsl -> resolveQuery(command, querydsl),
                        pageable
                ).map(depositMapper::mapToDomain);

        return new PagedSearchResult<>(page);
    }

    private JPQLQuery<DepositEntity> resolveQuery(DepositCommand command, Querydsl querydsl) {
        var qDepositEntity = QDepositEntity.depositEntity;

        var jpqlQuery = querydsl
                .createQuery(qDepositEntity)
                .select(qDepositEntity);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getId() != null) {
            predicate.and(qDepositEntity.id.eq(command.getId()));
        } else {
            LocalDate from = command.getFromBankDepositDate();
            if (from != null) {
                predicate.and(qDepositEntity.bankDepositDate.eq(from).or(qDepositEntity.bankDepositDate.after(from)));
            }

            LocalDate to = command.getToBankDepositDate();
            if (to != null) {
                predicate.and(qDepositEntity.bankDepositDate.eq(to).or(qDepositEntity.bankDepositDate.before(to)));
            }
        }

        return jpqlQuery.where(predicate);
    }
}
