package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface BalanceAccountEntityRepository extends CustomQuerydslJpaRepository<BalanceAccountEntity, Long>, QuerydslPredicateExecutor<BalanceAccountEntity> {

}
