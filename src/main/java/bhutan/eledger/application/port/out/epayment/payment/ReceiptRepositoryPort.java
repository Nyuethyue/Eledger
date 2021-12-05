package bhutan.eledger.application.port.out.epayment.payment;

import bhutan.eledger.domain.epayment.payment.ReceiptStatus;

import java.util.Collection;

public interface ReceiptRepositoryPort {

    void setStatuses(ReceiptStatus status, Collection<Long> receiptIds);
}
