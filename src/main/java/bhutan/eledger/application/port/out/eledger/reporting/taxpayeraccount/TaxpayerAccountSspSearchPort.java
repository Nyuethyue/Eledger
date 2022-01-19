package bhutan.eledger.application.port.out.eledger.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountSspDto;
import lombok.Data;

public interface TaxpayerAccountSspSearchPort {

    SearchResult<TaxpayerAccountSspDto> search(TaxpayerAccountSspSearchCommand command);

    @Data
    class TaxpayerAccountSspSearchCommand {

        private final String languageCode;
        private final String tpn;
        private final String glAccountPartFullCode;
        private final String periodYear;
        private final String periodSegment;
    }
}
