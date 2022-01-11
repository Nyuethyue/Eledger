package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounter;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;
import org.springframework.stereotype.Component;

@Component
class RefRRCOCashCounterMapper {
    RefRRCOCashCounterEntity mapToEntity(RefRRCOCashCounter refRRCOCashCounter) {
        RefRRCOCashCounterEntity refRRCOCashCounterEntity =
                new RefRRCOCashCounterEntity(
                        refRRCOCashCounter.getId(),
                        refRRCOCashCounter.getCode(),
                        refRRCOCashCounter.getValidityPeriod().getStart(),
                        refRRCOCashCounter.getValidityPeriod().getEnd()
                );

        refRRCOCashCounter.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefRRCOCashCounterDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refRRCOCashCounterEntity::addToDescriptions);
        return refRRCOCashCounterEntity;
    }

    RefRRCOCashCounter mapToDomain(RefRRCOCashCounterEntity refRRCOCashCounterEntity) {
        return RefRRCOCashCounter.withId(
                refRRCOCashCounterEntity.getId(),
                refRRCOCashCounterEntity.getCode(),
                ValidityPeriod.of(
                        refRRCOCashCounterEntity.getStartOfValidity(),
                        refRRCOCashCounterEntity.getEndOfValidity()
                ),
                Multilingual.of(refRRCOCashCounterEntity.getDescriptions())
        );
    }
}
