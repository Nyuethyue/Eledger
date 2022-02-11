package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface LoadTaxPeriodSegmentsUseCase {
    Collection<RefTaxPeriodSegment> findByTaxPeriodId(@NotNull Long taxPeriodTypeId);
}
