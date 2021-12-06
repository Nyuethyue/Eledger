package bhutan.eledger.application.port.out.eledger.transaction;

import bhutan.eledger.domain.eledger.transaction.PaymentAdviceData;

import java.util.Collection;

//todo repackage?
public interface EledgerGeneratePaymentAdvicePort {

    void generate(Collection<PaymentAdviceData> paymentAdviceDatas);
}
