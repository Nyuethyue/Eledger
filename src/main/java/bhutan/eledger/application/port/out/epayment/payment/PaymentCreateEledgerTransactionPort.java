package bhutan.eledger.application.port.out.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;

public interface PaymentCreateEledgerTransactionPort {

    void create(Receipt receipt);
}
