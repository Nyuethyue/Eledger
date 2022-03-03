package bhutan.eledger.application.port.out.eledger.refund;

import bhutan.eledger.domain.eledger.refund.RefundableTransactionData;

import java.time.LocalDate;
import java.util.Collection;

public interface GetRefundableDataByTransactionIdsPort {

    Collection<RefundableTransactionData> getByTransactionIds(String tpn, LocalDate calculationDate, Collection<Long> transactionIds);
}
