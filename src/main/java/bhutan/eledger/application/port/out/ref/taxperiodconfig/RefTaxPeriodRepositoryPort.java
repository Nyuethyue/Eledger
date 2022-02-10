package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;

import java.util.Optional;

public interface RefTaxPeriodRepositoryPort {

    Long create(RefTaxPeriodConfig bean);
    Long update(RefTaxPeriodConfig bean);

    Optional<RefTaxPeriodConfig> readBy(String taxTypeCode,
                                        Integer calendarYear,
                                        String taxPeriodCode,
                                        Long transactionTypeId);

    void deleteAll();
}
