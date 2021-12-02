package bhutan.eledger.application.port.out.epayment.payment;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public interface ReceiptSearchPort {

    SearchResult<Receipt> search(ReceiptCommand command);

    @Getter
    @ToString
    class ReceiptCommand extends AbstractSearchCommand {

        private final Long refCurrencyId;
        private final PaymentMode paymentMode;
        private final String branchCode;
        private final String glAccountPartFullCode;
        private final LocalDate receiptDate;

        public ReceiptCommand(int page, int size, String sortProperty, String sortDirection,
                              Long refCurrencyId, PaymentMode paymentMode, String branchCode, String glAccountPartFullCode, LocalDate receiptDate) {
            super(page, size, sortProperty, sortDirection);
            this.refCurrencyId = refCurrencyId;
            this.paymentMode = paymentMode;
            this.branchCode = branchCode;
            this.glAccountPartFullCode = glAccountPartFullCode;
            this.receiptDate = receiptDate;
        }
    }
}
