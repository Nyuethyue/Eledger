package bhutan.eledger.adapter.out.persistence.ref.bank;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.bank.RefBank;
import org.springframework.stereotype.Component;

@Component
class RefBankMapper {

    RefBankEntity mapToEntity(RefBank refBank) {
        RefBankEntity refBankEntity =
                new RefBankEntity(
                        refBank.getId(),
                        refBank.getCode()
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
                refBankEntity.getCode(),
                Multilingual.of(refBankEntity.getDescriptions())
        );
    }
}
