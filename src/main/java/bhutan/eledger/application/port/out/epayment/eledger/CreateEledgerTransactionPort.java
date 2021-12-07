package bhutan.eledger.application.port.out.epayment.eledger;

import bhutan.eledger.domain.epayment.payment.Receipt;

public interface CreateEledgerTransactionPort {

    void create(Receipt receipt);
}
