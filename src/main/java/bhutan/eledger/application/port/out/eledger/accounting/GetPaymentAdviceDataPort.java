package bhutan.eledger.application.port.out.eledger.accounting;

import bhutan.eledger.domain.eledger.accounting.PaymentAdviceData;

import java.time.LocalDate;
import java.util.Collection;

public interface GetPaymentAdviceDataPort {

    Collection<PaymentAdviceData> get(String tpn, LocalDate formulationDate);
}
