package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Optional;

@Validated
public interface ReadTaxPeriodTypesUseCase {
    Collection<RefTaxPeriodType> readAll();
    Optional<RefTaxPeriodType> readByCode(String code);
}
