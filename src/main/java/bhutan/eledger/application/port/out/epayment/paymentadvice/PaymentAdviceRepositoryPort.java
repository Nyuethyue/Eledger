package bhutan.eledger.application.port.out.epayment.paymentadvice;


import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;

import java.util.Collection;
import java.util.Optional;

public interface PaymentAdviceRepositoryPort {

    Optional<PaymentAdvice> readById(Long id);

    Optional<PaymentAdvice> readByDrnAndStatusIn(String drn, Collection<PaymentAdviceStatus> statuses);

    default PaymentAdvice requiredReadById(Long id) {
        return readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("PaymentAdvice by id: [" + id + "] not found.")
                );
    }

    Collection<PaymentAdvice> readAll();

    Long create(PaymentAdvice paymentAdvice);

    void deleteAll();

    void update(PaymentAdvice updatedPaymentAdvice);
}
