package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class GLAccountMapper {

    GLAccountEntity mapToEntity(GLAccount glAccount) {
        GLAccountEntity glAccountEntity = new GLAccountEntity(
                glAccount.getId(),
                glAccount.getCode(),
                glAccount.getGlAccountLastPartId(),
                glAccount.getCreationDateTime(),
                glAccount.getLastModificationDateTime()
        );

        glAccountEntity.setDescriptions(
                glAccount.getDescription()
                        .getTranslations()
                        .stream()
                        .map(t ->
                                new GLAccountDescriptionEntity(
                                        t.getId(),
                                        t.getLanguageCode(),
                                        t.getValue(),
                                        glAccountEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return glAccountEntity;
    }

    GLAccount mapToDomain(GLAccountEntity glAccountEntity) {
        return GLAccount.withId(
                glAccountEntity.getId(),
                glAccountEntity.getCode(),
                glAccountEntity.getCreationDateTime(),
                glAccountEntity.getLastModificationDateTime(),
                Multilingual.of(glAccountEntity.getDescriptions()),
                glAccountEntity.getGlAccountLastPartId()
        );
    }
}
