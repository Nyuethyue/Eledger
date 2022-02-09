package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;

import java.util.Optional;

public interface RefOpenCloseTaxPeriodRepositoryPort {
    Long create(RefOpenCloseTaxPeriod refOpenCloseTaxPeriodConfig);

    Optional<RefOpenCloseTaxPeriod> readByGlFullCodeYearTaxPeriodTransType(String glAccountPartFullCode,
                                                                           Integer calendarYear,
                                                                           Long taxPeriodTypeId,
                                                                           Long transactionTypeId);

    Optional<RefOpenCloseTaxPeriod> readById(Long id);

    void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod);
}