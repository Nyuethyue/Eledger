package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;

interface BalanceAccountEntityRepository extends CustomQuerydslJpaRepository<BalanceAccountEntity, Long>, QuerydslPredicateExecutor<BalanceAccountEntity>, RevisionRepository<BalanceAccountEntity, Long, Long> {

}
