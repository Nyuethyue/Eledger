package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;

import java.util.Collection;
import java.util.Optional;

public interface RefTaxPeriodTypeRepositoryPort {
    Collection<RefTaxPeriodType> readAll();
    Optional<RefTaxPeriodType> readByCode(String code);
}
