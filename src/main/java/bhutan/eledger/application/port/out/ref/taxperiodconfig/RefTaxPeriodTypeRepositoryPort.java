package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriod;

import java.util.Collection;
import java.util.Optional;

public interface RefTaxPeriodTypeRepositoryPort {
    Collection<RefTaxPeriod> readAll();
    Optional<RefTaxPeriod> readByCode(String code);
}
