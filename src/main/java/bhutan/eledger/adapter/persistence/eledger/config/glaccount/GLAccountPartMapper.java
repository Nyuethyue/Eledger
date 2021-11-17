package bhutan.eledger.adapter.persistence.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartStatus;
import org.springframework.stereotype.Component;

@Component
class GLAccountPartMapper {

    GLAccountPartEntity mapToEntity(GLAccountPart partDomain) {
        GLAccountPartEntity glAccountPartEntity = new GLAccountPartEntity(
                partDomain.getId(),
                partDomain.getCode(),
                partDomain.getParentId(),
                partDomain.getGlAccountPartLevelId(),
                partDomain.getStatus().getValue(),
                partDomain.getCreationDateTime(),
                partDomain.getLastModificationDateTime(),
                partDomain.getValidityPeriod().getStart(),
                partDomain.getValidityPeriod().getEnd()
        );

        partDomain.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new GLAccountPartDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(glAccountPartEntity::addToDescriptions);

        return glAccountPartEntity;
    }

    GLAccountPart mapToDomain(GLAccountPartEntity partEntity) {
        return GLAccountPart.withId(
                partEntity.getId(),
                partEntity.getCode(),
                partEntity.getParentId(),
                GLAccountPartStatus.of(partEntity.getStatus()),
                partEntity.getCreationDateTime(),
                partEntity.getLastModificationDateTime(),
                ValidityPeriod.of(partEntity.getStartOfValidity(), partEntity.getEndOfValidity()),
                Multilingual.of(partEntity.getDescriptions()),
                partEntity.getGlAccountPartTypeId()
        );
    }
}
