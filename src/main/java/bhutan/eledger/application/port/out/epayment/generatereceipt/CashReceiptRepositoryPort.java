package bhutan.eledger.application.port.out.epayment.generatereceipt;

import bhutan.eledger.domain.epayment.generatereceipt.CashReceipt;

public interface CashReceiptRepositoryPort {

    CashReceipt create(CashReceipt cashReceipt);

    void deleteAll();
}
