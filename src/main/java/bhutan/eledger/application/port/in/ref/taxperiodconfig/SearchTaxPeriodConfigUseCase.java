package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
public interface SearchTaxPeriodConfigUseCase {
    SearchResult<RefTaxPeriodConfig> search(@Valid SearchTaxPeriodConfigCommand command);

    @Data
    class SearchTaxPeriodConfigCommand {
        @NotNull
        @NotEmpty
        private final String taxTypeCode;

        @NotNull
        @Positive
        private final Integer calendarYear;

        @NotNull
        @NotEmpty
        private final String taxPeriodCode;

        @NotNull
        private final Long transactionTypeId;
    }
}
