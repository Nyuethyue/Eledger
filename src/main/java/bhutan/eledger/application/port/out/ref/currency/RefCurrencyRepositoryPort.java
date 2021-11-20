package bhutan.eledger.application.port.out.ref.currency;

import bhutan.eledger.domain.ref.currency.RefCurrency;

import java.util.Collection;
import java.util.Optional;

public interface RefCurrencyRepositoryPort {

    Long create(RefCurrency refCurrency);

    Collection<RefCurrency> readAll();

    void deleteAll();

    Optional<RefCurrency> readById(Long id);

    Optional<RefCurrency> readByCode(String code);

    boolean existsByCode(String code);

}
