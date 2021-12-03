package bhutan.eledger.application.port.in.epayment.payment;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Validated
public interface SearchReceiptForDetailsUseCase {
    SearchResult<Receipt> search(@Valid SearchReceiptForDetailsUseCase.SearchReceiptForDetailsCommand command);

    @Data
    class SearchReceiptForDetailsCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        private final String tpn;
        private final String receiptNumber;
        private final String otherReferenceNumber;
        private final LocalDate liabilityFromDate;
        private final LocalDate liabilityToDate;
        private final BigDecimal receiptFromAmount;
        private final BigDecimal receiptToAmount;
    }
}
