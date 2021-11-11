package bhutan.eledger.adapter.persistence.ref.currency;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import org.springframework.stereotype.Component;

@Component
class RefCurrencyMapper {

    RefCurrency mapToDomain(RefCurrencyEntity refCurrencyEntity) {
        return RefCurrency.withId(
                refCurrencyEntity.getId(),
                refCurrencyEntity.getCode(),
                Multilingual.of(refCurrencyEntity.getDescriptions())
        );
    }
}
