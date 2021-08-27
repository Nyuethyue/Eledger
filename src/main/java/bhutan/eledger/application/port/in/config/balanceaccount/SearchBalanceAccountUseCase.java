package bhutan.eledger.application.port.in.config.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.search.SearchResult;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public interface SearchBalanceAccountUseCase {

    SearchResult<BalanceAccount> search(SearchBalanceAccountCommand command);

    @Data
    class SearchBalanceAccountCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        private final String languageCode;
        private final String code;
        private final String head;

    }
}
