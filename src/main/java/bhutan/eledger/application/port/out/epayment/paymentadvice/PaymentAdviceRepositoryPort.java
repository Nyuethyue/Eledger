package bhutan.eledger.application.port.out.epayment.paymentadvice;


import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;

import java.util.Collection;
import java.util.Optional;

public interface PaymentAdviceRepositoryPort {

    Optional<PaymentAdvice> readById(Long id);

    Optional<PaymentAdvice> readByDrnAndStatusIn(String drn, Collection<PaymentAdviceStatus> statuses);

    Collection<PaymentAdvice> readAllByIds(Collection<Long> ids);

    default Collection<PaymentAdvice> requiredReadAllByIds(Collection<Long> ids) {
        var result = readAllByIds(ids);

        if (result.size() != ids.size()) {
            throw new RecordNotFoundException("Payment advice missed. Given ids: " + ids + ", founded ids: " + result.stream().map(PaymentAdvice::getId).toList());
        }

        return result;
    }

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

    void updateStatus(PaymentAdvice updatedPaymentAdvice);

    Optional<FlatPaymentAdvice> readFlatById(Long id);

    default FlatPaymentAdvice requiredReadFlatById(Long id) {
        return readFlatById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("PaymentAdvice by id: [" + id + "] not found.")
                );
    }

    Collection<FlatPaymentAdvice> readAllFlatByDrns(Collection<String> drns);


}
