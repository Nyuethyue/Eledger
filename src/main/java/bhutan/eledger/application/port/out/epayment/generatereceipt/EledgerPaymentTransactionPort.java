package bhutan.eledger.application.port.out.epayment.generatereceipt;

import bhutan.eledger.domain.epayment.generatereceipt.Receipt;

public interface EledgerPaymentTransactionPort {

    void create(Receipt receipt);
}
