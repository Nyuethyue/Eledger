package bhutan.eledger.application.port.out.epayment.payment;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;

public interface ReceiptSearchPort {

    SearchResult<Receipt> search(ReceiptCommand command);

    @Getter
    @ToString
    class ReceiptCommand extends AbstractSearchCommand {

        private final String currency;
        private final PaymentMode paymentMode;
        private final String branchCode;
        private final String glAccountPartFullCode;

        public ReceiptCommand(int page, int size, String sortProperty, String sortDirection, String currency, PaymentMode paymentMode, String branchCode, String glAccountPartFullCode) {
            super(page, size, sortProperty, sortDirection);
            this.currency = currency;
            this.paymentMode = paymentMode;
            this.branchCode = branchCode;
            this.glAccountPartFullCode = glAccountPartFullCode;
        }
    }
}
