package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;

import java.util.Optional;

public interface RefTaxPeriodRepositoryPort {

    Long create(RefTaxPeriodConfig bean);
    Long update(RefTaxPeriodConfig bean);

    Optional<RefTaxPeriodConfig> readBy(String taxTypeCode,
                                        Integer calendarYear,
                                        String taxPeriodCode,
                                        Long transactionTypeId);

    SearchResult<RefTaxPeriodConfig> search(String taxTypeCode,
                                            Integer calendarYear,
                                            String taxPeriodCode,
                                            Long transactionTypeId);


    void deleteAll();
}
