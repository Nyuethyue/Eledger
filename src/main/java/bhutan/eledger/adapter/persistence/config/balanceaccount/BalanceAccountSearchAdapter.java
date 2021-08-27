package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountSearchPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BalanceAccountSearchAdapter implements BalanceAccountSearchPort {
    private final BalanceAccountEntityRepository balanceAccountEntityRepository;
    private final BalanceAccountMapper balanceAccountMapper;

    @Override
    public SearchResult<BalanceAccount> search(BalanceAccountSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<BalanceAccount> page = balanceAccountEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(balanceAccountMapper::mapToDomain);

        return new PagedSearchResult<>(page);
    }

    private JPQLQuery<BalanceAccountEntity> resolveQuery(BalanceAccountSearchCommand command, Querydsl querydsl) {
        var qBalanceAccount = QBalanceAccountEntity.balanceAccountEntity;
        var qBalanceAccountDescription = QBalanceAccountDescriptionEntity.balanceAccountDescriptionEntity;


        var jpqlQuery = querydsl
                .createQuery(qBalanceAccount)
                .select(qBalanceAccount);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getCode() != null) {
            predicate.and(qBalanceAccount.code.startsWith(command.getCode()));
        }


        if (command.getHead() != null) {
            jpqlQuery = jpqlQuery.innerJoin(qBalanceAccountDescription)
                    .on(
                            qBalanceAccount.id.eq(qBalanceAccountDescription.balanceAccount.id)
                                    .and(qBalanceAccountDescription.languageCode.eq(command.getLanguageCode()))
                    );

            predicate.and(qBalanceAccountDescription.value.containsIgnoreCase(command.getHead()));
        }

        return jpqlQuery.where(predicate);
    }
}
