package bhutan.eledger.adapter.out.ref.persistence.currency;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import org.springframework.stereotype.Component;

@Component
class RefCurrencyMapper {

    RefCurrencyEntity mapToEntity(RefCurrency refCurrency) {
        RefCurrencyEntity refCurrencyEntity =
                new RefCurrencyEntity(
                        refCurrency.getId(),
                        refCurrency.getCode(),
                        refCurrency.getSymbol()
                );

        refCurrency.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefCurrencyDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refCurrencyEntity::addToDescriptions);

        return refCurrencyEntity;
    }

    RefCurrency mapToDomain(RefCurrencyEntity refCurrencyEntity) {
        return RefCurrency.withId(
                refCurrencyEntity.getId(),
                refCurrencyEntity.getCode(),
                refCurrencyEntity.getSymbol(),
                Multilingual.of(refCurrencyEntity.getDescriptions())
        );
    }
}
