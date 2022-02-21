package bhutan.eledger.adapter.out.epayment.persistence.paymentadvice;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Collection;
import java.util.Optional;

interface PaymentAdviceEntityRepository extends CustomQuerydslJpaRepository<PaymentAdviceEntity, Long>, QuerydslPredicateExecutor<PaymentAdviceEntity> {

    Optional<PaymentAdviceEntity> findByDrnAndStatusIn(String drn, Collection<String> statuses);

    Collection<FlatPaymentAdvice> findAllByDrnIn(Collection<String> drns);

    Optional<FlatPaymentAdvice> readById(Long id);

    @Query("UPDATE PaymentAdviceEntity pa SET pa.status = :status WHERE pa.id = :id")
    @Modifying
    void updateStatus(Long id, String status);
}
