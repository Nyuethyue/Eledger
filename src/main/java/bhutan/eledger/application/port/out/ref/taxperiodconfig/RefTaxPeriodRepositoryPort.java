package bhutan.eledger.application.port.out.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.bank.RefBank;

import java.util.Collection;
import java.util.Optional;

public interface RefTaxPeriodRepositoryPort {

    Long create(RefBank refBank);

    Collection<RefBank> readAll();

    void deleteAll();

    Optional<RefBank> readById(Long id);

    Optional<RefBank> readByCode(String code);

    boolean existsByCode(String code);

    boolean existsById(Long id);
}
