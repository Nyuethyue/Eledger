package bhutan.eledger.adapter.out.ref.persistence.agency;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.agency.RefAgency;
import org.springframework.stereotype.Component;

@Component
class RefAgencyMapper {
    RefAgencyEntity mapToEntity(RefAgency refAgency) {
        RefAgencyEntity refAgencyEntity =
                new RefAgencyEntity(
                        refAgency.getId(),
                        refAgency.getCode(),
                        refAgency.getValidityPeriod().getStart(),
                        refAgency.getValidityPeriod().getEnd()
                );

        refAgency.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefAgencyDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refAgencyEntity::addToDescriptions);

        return refAgencyEntity;
    }

    RefAgency mapToDomain(RefAgencyEntity refAgencyEntity) {
        return RefAgency.withId(
                refAgencyEntity.getId(),
                refAgencyEntity.getCode(),
                ValidityPeriod.of(
                        refAgencyEntity.getStartOfValidity(),
                        refAgencyEntity.getEndOfValidity()
                ),
                Multilingual.of(refAgencyEntity.getDescriptions())
        );
    }
}
