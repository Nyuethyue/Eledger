package bhutan.eledger.application.port.in.config.balanceaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public interface SearchBalanceAccountPartUseCase {

    SearchResult<BalanceAccountPart> search(SearchBalanceAccountPartCommand command);

    @Data
    class SearchBalanceAccountPartCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        @NotNull
        private final String languageCode;
        private final String code;
        private final String head;
        private final Integer partTypeId;

    }
}
