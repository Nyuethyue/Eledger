package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;

import java.util.Optional;

public interface RefOpenCloseTaxPeriodRepositoryPort {
    Long create(RefOpenCloseTaxPeriodConfig refOpenCloseTaxPeriodConfig);

    Optional<RefOpenCloseTaxPeriodConfig> readBy(String glAccountPartFullCode,
                                                 Integer calendarYear,
                                                 Long taxPeriodTypeId,
                                                 Long transactionTypeId);
}
