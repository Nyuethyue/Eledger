package bhutan.eledger.application.port.out.ref.currency;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.ref.currency.RefCurrency;

import java.util.Collection;
import java.util.Optional;

public interface RefCurrencyRepositoryPort {

    Long create(RefCurrency refCurrency);

    Collection<RefCurrency> readAll();

    void deleteAll();

    Optional<RefCurrency> readById(Long id);

    default RefCurrency requiredReadById(Long id) {
        return readById(id).orElseThrow(() ->
                new RecordNotFoundException("Currency by id: [" + id + "] not found.")
        );
    }

    Optional<RefCurrency> readByCode(String code);

    default RefCurrency requiredReadByCode(String code) {
        return readByCode(code).orElseThrow(() ->
                new RecordNotFoundException("Currency by code: [" + code + "] not found.")
        );
    }

    boolean existsByCode(String code);

}
