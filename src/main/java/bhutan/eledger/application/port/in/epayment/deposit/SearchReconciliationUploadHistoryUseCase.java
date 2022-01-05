package bhutan.eledger.application.port.in.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Validated
public interface SearchReconciliationUploadHistoryUseCase {

    SearchResult<Receipt> search(@Valid SearchReconciliationUploadRecordCommand command);

    @Data
    class SearchReconciliationUploadRecordCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;
        private final LocalDate fromDate;
        private final LocalDate toDate;
    }
}
