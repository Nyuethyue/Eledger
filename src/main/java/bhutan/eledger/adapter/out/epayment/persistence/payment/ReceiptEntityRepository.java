package bhutan.eledger.adapter.out.epayment.persistence.payment;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.epayment.payment.FlatReceipt;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

interface ReceiptEntityRepository extends CustomQuerydslJpaRepository<ReceiptEntity, Long> {

    Collection<FlatReceipt> findAllByIdIn(Collection<Long> ids);

    @Query(value = """
            SELECT DISTINCT r.receipt_number FROM epayment.ep_receipt r
                     INNER JOIN epayment.ep_payment p ON p.receipt_id = r.id
                     INNER JOIN epayment.ep_pa_payable_line pl ON p.payable_line_id = pl.id
                     INNER JOIN epayment.ep_payment_advice pa ON pl.payment_advice_id = pa.id
                     INNER JOIN epayment.ep_rma_message rm ON pa.id = rm.payment_advice_id
            WHERE rm.order_no = :orderNo
            """, nativeQuery = true)
    String getReceiptNumberByPaId(String orderNo);
}
