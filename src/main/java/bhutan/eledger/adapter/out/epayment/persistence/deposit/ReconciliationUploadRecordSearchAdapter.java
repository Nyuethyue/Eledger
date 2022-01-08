package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.epayment.deposit.reconciliation.ReconciliationUploadRecordHistorySearchPort;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
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
class ReconciliationUploadRecordSearchAdapter implements ReconciliationUploadRecordHistorySearchPort {
    private final ReconciliationUploadRecordMapper mapper;
    private final ReconciliationUploadRecordEntityRepository repository;

    @Override
    public SearchResult<ReconciliationUploadRecordInfo> search(ReconciliationUploadRecordHistorySearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<ReconciliationUploadRecordInfo> page = repository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(mapper::mapToDomain);

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<ReconciliationUploadRecordEntity> resolveQuery(ReconciliationUploadRecordHistorySearchCommand command, Querydsl querydsl) {
        var qReconciliationUploadRecordEntity = QReconciliationUploadRecordEntity.reconciliationUploadRecordEntity;

        var jpqlQuery = querydsl
                .createQuery(qReconciliationUploadRecordEntity)
                .select(qReconciliationUploadRecordEntity);

        BooleanBuilder predicate = new BooleanBuilder();


        LocalDate from = command.getFromDate();
        if (from != null) {
            predicate.and(qReconciliationUploadRecordEntity.creationDateTime.goe(from.atStartOfDay()));
        }

        LocalDate to = command.getToDate();
        if (to != null) {
            predicate.and(qReconciliationUploadRecordEntity.creationDateTime.loe(to.plusDays(1).atStartOfDay()));
        }

        return jpqlQuery.where(predicate);
    }
}
