package bhutan.eledger.application.port.out.eledger.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountDto;
import lombok.Data;

import java.time.LocalDate;

public interface TaxpayerAccountSearchPort {

    SearchResult<TaxpayerAccountDto> search(TaxpayerAccountSearchCommand command);

    @Data
    class TaxpayerAccountSearchCommand {

        private final Integer page;
        private final Integer size;
        private final String languageCode;
        private final String tpn;
        private final String glAccountPartFullCode;
        private final String periodYear;
        private final String periodSegment;
        private final LocalDate startTransactionDate;
        private final LocalDate endTransactionDate;
    }
}
