package bhutan.eledger.application.port.out.ref.denomination;

import bhutan.eledger.domain.ref.denomination.RefDenomination;

import java.util.Collection;
import java.util.Optional;

public interface RefDenominationRepositoryPort {

    Long create(RefDenomination refDenomination);

    Collection<RefDenomination> readAll();

    void deleteAll();

    Optional<RefDenomination> readById(Long id);

    boolean existsByValue(String value);

}
