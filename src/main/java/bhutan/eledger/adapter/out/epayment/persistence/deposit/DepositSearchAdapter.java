package bhutan.eledger.adapter.out.epayment.persistence.deposit;

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
import java.util.*;

@Component
@RequiredArgsConstructor
class DepositSearchAdapter implements DepositSearchPort {
    private final DepositEntityRepository depositEntityRepository;
    private final FlatReceiptLoaderBean flatReceiptLoaderBean;
    private final DepositMapper depositMapper;


    @Override
    public SearchResult<Deposit> search(DepositSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<DepositEntity> rawPage = depositEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        );

        Collection<DepositEntity> depositList = new LinkedList<>();
        rawPage.map(depositList::add);

        Page<Deposit> page = rawPage.map(depositEntity ->
                depositMapper.mapToDomain(depositEntity, flatReceiptLoaderBean.loadReceiptIdToFlatReceiptMap(depositList)));

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<DepositEntity> resolveQuery(DepositSearchCommand command, Querydsl querydsl) {
        var qDepositEntity = QDepositEntity.depositEntity;

        var jpqlQuery = querydsl
                .createQuery(qDepositEntity)
                .select(qDepositEntity);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getId() != null) {
            predicate.and(qDepositEntity.id.eq(command.getId()));
        }

        if (command.getDepositNumber() != null) {
            predicate.and(qDepositEntity.depositNumber.eq(command.getDepositNumber()));
        }

        LocalDate from = command.getFromBankDepositDate();
        if (from != null) {
            predicate.and(qDepositEntity.bankDepositDate.goe(from));
        }

        LocalDate to = command.getToBankDepositDate();
        if (to != null) {
            predicate.and(qDepositEntity.bankDepositDate.loe(to));
        }

        return jpqlQuery.where(predicate);
    }
}