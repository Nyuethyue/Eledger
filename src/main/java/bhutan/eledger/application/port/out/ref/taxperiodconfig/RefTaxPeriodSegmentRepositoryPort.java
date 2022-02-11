package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;

import java.util.List;

public interface RefTaxPeriodSegmentRepositoryPort {
    List<RefTaxPeriodSegment> loadByTaxPeriodTypeId(Long taxPeriodTypeId);
}
