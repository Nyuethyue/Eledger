package bhutan.eledger.application.port.in.eledger.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountSspDto;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface SearchTaxpayerAccountSspUseCase {

    SearchResult<TaxpayerAccountSspDto> search(@Valid SearchTaxpayerAccountSspCommand command);

    @Data
    class SearchTaxpayerAccountSspCommand {

        @NotNull
        private final String languageCode;

        @NotNull
        private final String tpn;

        @NotNull
        private final String glAccountPartFullCode;
        @NotNull
        private final String periodYear;
        private final String periodSegment;
    }

}
