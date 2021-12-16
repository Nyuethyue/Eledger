package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import org.springframework.stereotype.Component;

@Component
class GLAccountPartMapper {

    GLAccountPartEntity mapToEntity(GLAccountPart partDomain) {

        GLAccountPartEntity glAccountPartEntity = new GLAccountPartEntity(
                partDomain.getId(),
                partDomain.getCode(),
                partDomain.getFullCode(),
                partDomain.getParentId(),
                partDomain.getGlAccountPartLevelId(),
                partDomain.getCreationDateTime(),
                partDomain.getLastModificationDateTime()
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
                partEntity.getFullCode(),
                partEntity.getParentId(),
                partEntity.getCreationDateTime(),
                partEntity.getLastModificationDateTime(),
                Multilingual.of(partEntity.getDescriptions()),
                partEntity.getGlAccountPartTypeId()
        );
    }
}
