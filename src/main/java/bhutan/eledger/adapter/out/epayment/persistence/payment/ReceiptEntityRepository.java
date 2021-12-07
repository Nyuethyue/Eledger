package bhutan.eledger.adapter.out.epayment.persistence.payment;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;

public interface ReceiptEntityRepository extends CustomQuerydslJpaRepository<ReceiptEntity, Long> {
}
