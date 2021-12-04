package bhutan.eledger.application.port.out.epayment.payment;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReceiptForDetailsSearchPort {
    SearchResult<Receipt> search(ReceiptForDetailsCommand command);

    @Getter
    @ToString
    class ReceiptForDetailsCommand extends AbstractSearchCommand {

        private final String tpn;
        private final String receiptNumber;
        private final String otherReferenceNumber;
        private final LocalDate liabilityFromDate;
        private final LocalDate liabilityToDate;
        private final BigDecimal receiptFromAmount;
        private final BigDecimal receiptToAmount;

        public ReceiptForDetailsCommand(int page, int size, String sortProperty, String sortDirection,
                                        String tpn, String receiptNumber, String otherReferenceNumber,
                                        LocalDate liabilityFromDate,LocalDate liabilityToDate,
                                        BigDecimal receiptFromAmount,BigDecimal receiptToAmount) {
            super(page, size, sortProperty, sortDirection);
            this.tpn = tpn;
            this.receiptNumber = receiptNumber;
            this.otherReferenceNumber = otherReferenceNumber;
            this.liabilityFromDate = liabilityFromDate;
            this.liabilityToDate = liabilityToDate;
            this.receiptFromAmount = receiptFromAmount;
            this.receiptToAmount = receiptToAmount;
        }
    }
}