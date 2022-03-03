package bhutan.eledger.application.port.out.eledger.refund;

import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import bhutan.eledger.domain.eledger.refund.RefundableTransactionData;

import java.time.LocalDate;
import java.util.Collection;

public interface GetRefundableDataByTaxTypesPort {

    Collection<RefundableTransactionData> getByTaxTypes(String tpn, LocalDate calculationDate, Collection<GLAccountPart> taxTypes);
}
