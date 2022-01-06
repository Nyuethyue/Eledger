package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounters;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;
import org.springframework.stereotype.Component;

@Component
class RefRRCOCashCountersMapper {
    RefRRCOCashCountersEntity mapToEntity(RefRRCOCashCounters refRRCOCashCounters) {
        RefRRCOCashCountersEntity refRRCOCashCountersEntity =
                new RefRRCOCashCountersEntity(
                        refRRCOCashCounters.getId(),
                        refRRCOCashCounters.getCode(),
                        refRRCOCashCounters.getValidityPeriod().getStart(),
                        refRRCOCashCounters.getValidityPeriod().getEnd()
                );

        refRRCOCashCounters.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefRRCOCashCountersDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refRRCOCashCountersEntity::addToDescriptions);
        return refRRCOCashCountersEntity;
    }

    RefRRCOCashCounters mapToDomain(RefRRCOCashCountersEntity refRRCOCashCountersEntity) {
        return RefRRCOCashCounters.withId(
                refRRCOCashCountersEntity.getId(),
                refRRCOCashCountersEntity.getCode(),
                ValidityPeriod.of(
                        refRRCOCashCountersEntity.getStartOfValidity(),
                        refRRCOCashCountersEntity.getEndOfValidity()
                ),
                Multilingual.of(refRRCOCashCountersEntity.getDescriptions())
        );
    }
}
