package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartSearchPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BalanceAccountPartSearchAdapter implements BalanceAccountPartSearchPort {
    private final BalanceAccountPartEntityRepository balanceAccountPartEntityRepository;
    private final BalanceAccountPartMapper balanceAccountPartMapper;

    @Override
    public SearchResult<BalanceAccountPart> search(BalanceAccountPartSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<BalanceAccountPart> page = balanceAccountPartEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(balanceAccountPartMapper::mapToDomain);

        return new PagedSearchResult<>(page);
    }

    private JPQLQuery<BalanceAccountPartEntity> resolveQuery(BalanceAccountPartSearchCommand command, Querydsl querydsl) {
        var qBalanceAccountPart = QBalanceAccountPartEntity.balanceAccountPartEntity;
        var qBalanceAccountPartDescription = QBalanceAccountPartDescriptionEntity.balanceAccountPartDescriptionEntity;


        var jpqlQuery = querydsl
                .createQuery(qBalanceAccountPart)
                .select(qBalanceAccountPart);

        BooleanBuilder predicate = new BooleanBuilder(qBalanceAccountPart.balanceAccountPartTypeId.eq(command.getPartTypeId()));

        if (command.getCode() != null) {
            predicate.and(qBalanceAccountPart.code.startsWith(command.getCode()));
        }


        if (command.getHead() != null) {
            jpqlQuery = jpqlQuery.innerJoin(qBalanceAccountPartDescription)
                    .on(
                            qBalanceAccountPart.id.eq(qBalanceAccountPartDescription.balanceAccountPart.id)
                                    .and(qBalanceAccountPartDescription.languageCode.eq(command.getLanguageCode()))
                    );

            predicate.and(qBalanceAccountPartDescription.value.containsIgnoreCase(command.getHead()));
        }

        return jpqlQuery.where(predicate);
    }
}
