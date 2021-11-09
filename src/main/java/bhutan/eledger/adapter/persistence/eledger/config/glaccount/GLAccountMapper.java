package bhutan.eledger.adapter.persistence.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountStatus;
import bhutan.eledger.domain.eledger.config.glaccount.ValidityPeriod;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class GLAccountMapper {

    GLAccountEntity mapToEntity(GLAccount glAccount) {
        GLAccountEntity glAccountEntity = new GLAccountEntity(
                glAccount.getId(),
                glAccount.getCode(),
                glAccount.getGlAccountLastPartId(),
                glAccount.getStatus().getValue(),
                glAccount.getCreationDateTime(),
                glAccount.getLastModificationDateTime(),
                glAccount.getValidityPeriod().getStart(),
                glAccount.getValidityPeriod().getEnd()
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
                GLAccountStatus.of(glAccountEntity.getStatus()),
                glAccountEntity.getCreationDateTime(),
                glAccountEntity.getLastModificationDateTime(),
                ValidityPeriod.of(glAccountEntity.getStartOfValidity(), glAccountEntity.getEndOfValidity()),
                Multilingual.of(glAccountEntity.getDescriptions()),
                glAccountEntity.getGlAccountLastPartId()
        );
    }
}
