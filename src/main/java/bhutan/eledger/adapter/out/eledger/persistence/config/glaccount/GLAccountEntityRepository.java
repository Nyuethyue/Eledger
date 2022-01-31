package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;

interface GLAccountEntityRepository extends CustomQuerydslJpaRepository<GLAccountEntity, Long>, QuerydslPredicateExecutor<GLAccountEntity>, RevisionRepository<GLAccountEntity, Long, Long> {
    boolean existsByCode(String code);
}
