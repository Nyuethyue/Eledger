package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadTaxPeriodTypesUseCase {
    Collection<RefTaxPeriodType> readAll();
}
