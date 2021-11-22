package bhutan.eledger.application.port.out.epayment.paymentadvice;


import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;

import java.util.Collection;
import java.util.Optional;

public interface PaymentAdviceRepositoryPort {

    Optional<PaymentAdvice> readById(Long id);

    Collection<PaymentAdvice> readAll();

    Long create(PaymentAdvice paymentAdvice);

    void deleteAll();

}
