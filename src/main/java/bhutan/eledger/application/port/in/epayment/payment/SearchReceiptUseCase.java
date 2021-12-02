package bhutan.eledger.application.port.in.epayment.payment;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Validated
public interface SearchReceiptUseCase {

    SearchResult<Receipt> search(@Valid SearchReceiptCommand command);

    @Data
    class SearchReceiptCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        private final Long refCurrencyId;
        private final PaymentMode paymentMode;
        private final String branchCode;
        private final String glAccountPartFullCode;
        private final LocalDate receiptDate;
    }
}
