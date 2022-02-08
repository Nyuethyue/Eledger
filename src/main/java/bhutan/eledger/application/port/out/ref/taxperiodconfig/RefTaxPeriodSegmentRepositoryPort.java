package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;

import java.util.Collection;

public interface RefTaxPeriodSegmentRepositoryPort {
    Collection<RefTaxPeriodSegment> loadByTaxPeriodTypeId(Long taxPeriodTypeId);
}
