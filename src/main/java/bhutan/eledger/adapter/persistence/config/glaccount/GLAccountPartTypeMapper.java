package bhutan.eledger.adapter.persistence.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.glaccount.GLAccountPartType;
import org.springframework.stereotype.Component;

@Component
class GLAccountPartTypeMapper {

    GLAccountPartTypeEntity mapToEntity(GLAccountPartType partTypeDomain) {
        GLAccountPartTypeEntity glAccountPartTypeEntity =
                new GLAccountPartTypeEntity(
                        partTypeDomain.getId(),
                        partTypeDomain.getLevel(),
                        partTypeDomain.getCreationDateTime(),
                        partTypeDomain.getLastModificationDateTime()
                );

        partTypeDomain.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new GLAccountPartTypeDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(glAccountPartTypeEntity::addToDescriptions);

        return glAccountPartTypeEntity;
    }

    GLAccountPartType mapToDomain(GLAccountPartTypeEntity partTypeEntity) {
        return GLAccountPartType.withId(
                partTypeEntity.getId(),
                partTypeEntity.getLevel(),
                partTypeEntity.getCreationDateTime(),
                partTypeEntity.getLastModificationDateTime(),
                Multilingual.of(partTypeEntity.getDescriptions())
        );
    }
}
