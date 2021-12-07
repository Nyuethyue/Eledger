package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;

public interface DepositEntityRepository extends CustomQuerydslJpaRepository<DepositEntity, Long> {
}
