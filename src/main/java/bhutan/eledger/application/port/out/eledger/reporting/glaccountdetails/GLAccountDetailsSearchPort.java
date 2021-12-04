package bhutan.eledger.application.port.out.eledger.reporting.glaccountdetails;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.eledger.reporting.glaccountdetails.GlAccountDetailsDto;
import lombok.Data;

import java.time.LocalDate;

public interface GLAccountDetailsSearchPort {

    SearchResult<GlAccountDetailsDto> search(GLAccountDetailsSearchCommand command);

    @Data
    class GLAccountDetailsSearchCommand {

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
