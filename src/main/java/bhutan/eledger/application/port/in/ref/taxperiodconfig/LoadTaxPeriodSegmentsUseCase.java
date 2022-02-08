package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface LoadTaxPeriodSegmentsUseCase {
    Collection<RefTaxPeriodSegment> findByTaxPeriodTypeId(Long taxPeriodTypeId);
}
