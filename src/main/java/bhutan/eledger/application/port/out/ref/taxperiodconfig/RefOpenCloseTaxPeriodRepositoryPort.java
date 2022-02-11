package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;

import java.util.Optional;

public interface RefOpenCloseTaxPeriodRepositoryPort {
    Long create(RefOpenCloseTaxPeriod refOpenCloseTaxPeriodConfig);

    Optional<RefOpenCloseTaxPeriod> readByGlFullCodeYearTaxPeriodTransType(String glAccountPartFullCode,
                                                                           Integer calendarYear,
                                                                           String taxPeriodCode,
                                                                           Long transactionTypeId);

    Optional<RefOpenCloseTaxPeriod> readById(Long id);

    void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod);
}