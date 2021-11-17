package bhutan.eledger.adapter.persistence.ref.bank;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.bank.RefBank;
import org.springframework.stereotype.Component;

@Component
public class RefBankMapper {

    RefBankEntity mapToEntity(RefBank refBank) {
        RefBankEntity refBankEntity =
                new RefBankEntity(
                        refBank.getId(),
                        refBank.getBankName(),
                        refBank.getBfscCode()
                );

        refBank.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefBankDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refBankEntity::addToDescriptions);

        return refBankEntity;
    }

    RefBank mapToDomain(RefBankEntity refBankEntity) {
        return RefBank.withId(
                refBankEntity.getId(),
                refBankEntity.getBankName(),
                refBankEntity.getBfscCode(),
                Multilingual.of(refBankEntity.getDescriptions())
        );
    }
}
