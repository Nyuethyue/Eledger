package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriod;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Validated
public interface ReadTaxPeriodTypesUseCase {
    Collection<RefTaxPeriod> readAll();
    Optional<RefTaxPeriod> readByCode(@NotNull String code);
}
