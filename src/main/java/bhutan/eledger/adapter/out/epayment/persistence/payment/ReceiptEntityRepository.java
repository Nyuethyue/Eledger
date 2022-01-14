package bhutan.eledger.adapter.out.epayment.persistence.payment;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.epayment.payment.FlatReceipt;

import java.util.Collection;

interface ReceiptEntityRepository extends CustomQuerydslJpaRepository<ReceiptEntity, Long> {

    Collection<FlatReceipt> findAllByIdIn(Collection<Long> ids);
}
