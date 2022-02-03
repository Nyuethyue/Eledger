package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;

import java.util.Optional;

public interface RefTaxPeriodRepositoryPort {

    Long create(RefTaxPeriodConfig bean);

    Optional<RefTaxPeriodConfig> readBy(String taxTypeCode,
                                        Integer calendarYear,
                                        Long taxPeriodTypeId,
                                        Long transactionTypeId);

    void deleteAll();
}
