package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartSearchPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GLAccountPartSearchAdapter implements GLAccountPartSearchPort {
    private final GLAccountPartEntityRepository glAccountPartEntityRepository;
    private final GLAccountPartMapper glAccountPartMapper;

    @Override
    public SearchResult<GLAccountPart> search(GLAccountPartSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<GLAccountPart> page = glAccountPartEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(glAccountPartMapper::mapToDomain);

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<GLAccountPartEntity> resolveQuery(GLAccountPartSearchCommand command, Querydsl querydsl) {
        var qglAccountPart = QGLAccountPartEntity.gLAccountPartEntity;
        var qglAccountPartDescription = QGLAccountPartDescriptionEntity.gLAccountPartDescriptionEntity;


        var jpqlQuery = querydsl
                .createQuery(qglAccountPart)
                .select(qglAccountPart);

        BooleanBuilder predicate = new BooleanBuilder(qglAccountPart.glAccountPartTypeId.eq(command.getPartTypeId()));

        if (command.getCode() != null) {
            predicate.and(qglAccountPart.code.startsWith(command.getCode()));
        }


        if (command.getHead() != null) {
            jpqlQuery = jpqlQuery.innerJoin(qglAccountPartDescription)
                    .on(
                            qglAccountPart.id.eq(qglAccountPartDescription.glAccountPart.id)
                                    .and(qglAccountPartDescription.languageCode.eq(command.getLanguageCode()))
                    );

            predicate.and(qglAccountPartDescription.value.containsIgnoreCase(command.getHead()));
        }

        return jpqlQuery.where(predicate);
    }
}
