package bhutan.eledger.application.port.out.eledger.transaction;

import bhutan.eledger.domain.eledger.transaction.PaymentAdviceData;

import java.time.LocalDate;
import java.util.Collection;

public interface GetPaymentAdviceDataPort {

    Collection<PaymentAdviceData> get(String tpn, LocalDate formulationDate);
}
