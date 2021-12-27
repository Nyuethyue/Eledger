package bhutan.eledger.adapter.out.epayment.persistence.paymentadvice;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Collection;
import java.util.Optional;

interface PaymentAdviceEntityRepository extends CustomQuerydslJpaRepository<PaymentAdviceEntity, Long>, QuerydslPredicateExecutor<PaymentAdviceEntity> {

    Optional<PaymentAdviceEntity> findByDrnAndStatusIn(String drn, Collection<String> statuses);

    @Query("select distinct pa from PaymentAdviceEntity pa join pa.payableLines pl where pl.")
    Collection<PaymentAdvice> fpa();
}
