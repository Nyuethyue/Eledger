package bhutan.eledger.adapter.persistence.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface PaymentAdviceEntityRepository extends CustomQuerydslJpaRepository<PaymentAdviceEntity, Long>, QuerydslPredicateExecutor<PaymentAdviceEntity> {
}
