package bhutan.eledger.application.port.out.eledger.epayment;

import bhutan.eledger.domain.eledger.accounting.PaymentAdviceData;

import java.util.Collection;

public interface UpsertEpaymentPaymentAdvicePort {

    void upsert(Collection<PaymentAdviceData> paymentAdviceDatas);
}
