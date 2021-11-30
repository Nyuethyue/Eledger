package bhutan.eledger.adapter.out.persistence.ref.denomination;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.denomination.RefDenomination;
import org.springframework.stereotype.Component;

@Component
class RefDenominationMapper {

    RefDenominationEntity mapToEntity(RefDenomination refCurrency) {
        RefDenominationEntity refCurrencyEntity =
                new RefDenominationEntity(
                        refCurrency.getId(),
                        refCurrency.getCode(),
                        refCurrency.getSymbol()
                );

        refCurrency.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefDenominationDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refCurrencyEntity::addToDescriptions);

        return refCurrencyEntity;
    }

    RefDenomination mapToDomain(RefDenominationEntity refCurrencyEntity) {
        return RefDenomination.withId(
                refCurrencyEntity.getId(),
                refCurrencyEntity.getCode(),
                refCurrencyEntity.getSymbol(),
                Multilingual.of(refCurrencyEntity.getDescriptions())
        );
    }
}
