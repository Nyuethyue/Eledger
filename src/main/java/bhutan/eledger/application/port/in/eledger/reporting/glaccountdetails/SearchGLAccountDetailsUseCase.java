package bhutan.eledger.application.port.in.eledger.reporting.glaccountdetails;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.eledger.reporting.glaccountdetails.GlAccountDetailsDto;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Validated
public interface SearchGLAccountDetailsUseCase {

    SearchResult<GlAccountDetailsDto> search(@Valid SearchGLAccountDetailsCommand command);

    @Data
    class SearchGLAccountDetailsCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;

        @NotNull
        private final String languageCode;

        @NotNull
        private final String tpn;

        private final String glAccountPartFullCode;
        private final String periodYear;
        private final String periodSegment;
        private final LocalDate startTransactionDate;
        private final LocalDate endTransactionDate;
    }

}
