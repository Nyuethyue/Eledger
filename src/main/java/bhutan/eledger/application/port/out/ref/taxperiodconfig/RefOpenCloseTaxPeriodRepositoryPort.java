package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;

public interface RefOpenCloseTaxPeriodRepositoryPort {
    Long create(RefOpenCloseTaxPeriodConfig refOpenCloseTaxPeriodConfig);
}
