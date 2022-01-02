package bhutan.eledger.application.port.out.epayment.payment;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collection;

public interface ReceiptSearchPort {

    SearchResult<Receipt> search(ReceiptCommand command);

    @Getter
    @ToString
    class ReceiptCommand extends AbstractSearchCommand {

        private final Long refCurrencyId;
        private final PaymentMode paymentMode;
        private final Long bankBranchId;
        private final Long bankIssuingBranchId;
        private final String glAccountPartFullCode;
        private final LocalDate receiptDate;
        private final Collection<String> statuses;

        public ReceiptCommand(int page, int size, String sortProperty, String sortDirection,
                              Long refCurrencyId, PaymentMode paymentMode, Long bankBranchId, Long bankIssuingBranchId,
                              String glAccountPartFullCode, LocalDate receiptDate,
                              Collection<String> statuses) {
            super(page, size, sortProperty, sortDirection);
            this.refCurrencyId = refCurrencyId;
            this.paymentMode = paymentMode;
            this.bankBranchId = bankBranchId;
            this.bankIssuingBranchId = bankIssuingBranchId;
            this.glAccountPartFullCode = glAccountPartFullCode;
            this.receiptDate = receiptDate;
            this.statuses = statuses;
        }
    }
}
