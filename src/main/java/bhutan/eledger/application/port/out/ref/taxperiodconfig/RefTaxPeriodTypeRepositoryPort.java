package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;

import java.util.Collection;

public interface RefTaxPeriodTypeRepositoryPort {
    Collection<RefTaxPeriodType> readAll();
}
