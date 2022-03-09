package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.SearchTaxPeriodConfigUseCase;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface RefTaxPeriodRepositoryPort {

    Long create(RefTaxPeriodConfig bean);
    Long update(RefTaxPeriodConfig bean);
    void delete(Long id);

    Optional<RefTaxPeriodConfig> readBy(String taxTypeCode,
                                        Integer calendarYear,
                                        String taxPeriodCode,
                                        Long transactionTypeId,
                                        LocalDate validFrom,
                                        LocalDate validTo);

    Optional<RefTaxPeriodConfig> findById(Long id);


    SearchResult<RefTaxPeriodConfig> search(SearchTaxPeriodConfigUseCase.SearchTaxPeriodConfigCommand command);

    Collection<RefTaxPeriodConfig> searchAll(String taxTypeCode,
                                          Integer calendarYear,
                                          String taxPeriodCode,
                                          Long transactionTypeId);

    void deleteAll();
}
