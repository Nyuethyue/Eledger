package bhutan.eledger.adapter.persistence.config.glaccount;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountSearchPort;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GLAccountSearchAdapter implements GLAccountSearchPort {
    private final GLAccountEntityRepository glAccountEntityRepository;
    private final GLAccountMapper glAccountMapper;

    @Override
    public SearchResult<GLAccount> search(GLAccountSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<GLAccount> page = glAccountEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(glAccountMapper::mapToDomain);

        return new PagedSearchResult<>(page);
    }

    private JPQLQuery<GLAccountEntity> resolveQuery(GLAccountSearchCommand command, Querydsl querydsl) {


        var qglAccount = QGLAccountEntity.gLAccountEntity;
        var qglAccountDescription = QGLAccountDescriptionEntity.gLAccountDescriptionEntity;


        var jpqlQuery = querydsl
                .createQuery(qglAccount)
                .select(qglAccount);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getCode() != null) {
            predicate.and(qglAccount.code.startsWith(command.getCode()));
        }


        if (command.getHead() != null) {
            jpqlQuery = jpqlQuery.innerJoin(qglAccountDescription)
                    .on(
                            qglAccount.id.eq(qglAccountDescription.glAccount.id)
                                    .and(qglAccountDescription.languageCode.eq(command.getLanguageCode()))
                    );

            predicate.and(qglAccountDescription.value.containsIgnoreCase(command.getHead()));
        }

        return jpqlQuery.where(predicate);
    }
}
