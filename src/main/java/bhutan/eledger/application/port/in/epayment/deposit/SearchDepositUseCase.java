package bhutan.eledger.application.port.in.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Validated
public interface SearchDepositUseCase {

    SearchResult<Deposit> search(@Valid SearchDepositCommand command);

    @Data
    class SearchDepositCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        private final Long id;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private final LocalDate fromBankDepositDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private final LocalDate toBankDepositDate;
    }
}
