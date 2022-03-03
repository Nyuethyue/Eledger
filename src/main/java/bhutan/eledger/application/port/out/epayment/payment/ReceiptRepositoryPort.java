package bhutan.eledger.application.port.out.epayment.payment;

import bhutan.eledger.domain.epayment.payment.FlatReceipt;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;

import java.util.Collection;

public interface ReceiptRepositoryPort {
    Receipt create(Receipt receipt);

    void deleteAll();

    void checkReceipts(Collection<Long> receiptIds);

    void updateStatuses(ReceiptStatus status, Collection<Long> receiptIds);

    Collection<FlatReceipt> readAllByIds(Collection<Long> ids);

    String getReceiptNumberByPaId(String orderNo);
}
