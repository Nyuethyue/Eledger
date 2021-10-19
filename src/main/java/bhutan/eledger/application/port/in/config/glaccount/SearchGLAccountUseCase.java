package bhutan.eledger.application.port.in.config.glaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public interface SearchGLAccountUseCase {

    SearchResult<GLAccount> search(SearchGLAccountCommand command);

    @Data
    class SearchGLAccountCommand {
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

    }
}
