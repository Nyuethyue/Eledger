package bhutan.eledger.application.port.out.epayment.payment;

import bhutan.eledger.domain.epayment.payment.CashReceipt;

public interface CashReceiptRepositoryPort {

    CashReceipt create(CashReceipt cashReceipt);

    void deleteAll();
}
