package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.bankaccount.BankAccountGLAccountPart;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import org.springframework.stereotype.Component;

@Component
class RefBankAccountMapper {
    RefBankAccountEntity mapToEntity(RefBankAccount refBankAccount) {


        var BankAccountGLAccountPartEntity = new BankAccountGLAccountPartEntity(
                refBankAccount.getBankAccountGLAccountPart().getId(),
                refBankAccount.getBankAccountGLAccountPart().getCode()
        );
        RefBankAccountEntity refBankAccountEntity =
                new RefBankAccountEntity(
                        refBankAccount.getId(),
                        refBankAccount.getBranchId(),
                        refBankAccount.getCode(),
                        refBankAccount.getValidityPeriod().getStart(),
                        refBankAccount.getValidityPeriod().getEnd(),
                        refBankAccount.getIsPrimaryForGlAccount(),
                        BankAccountGLAccountPartEntity
                );

        refBankAccount.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefBankAccountDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refBankAccountEntity::addToDescriptions);

        return refBankAccountEntity;
    }

    RefBankAccount mapToDomain(RefBankAccountEntity refBankAccountEntity) {
        BankAccountGLAccountPart bankAccountGLAccountPart = BankAccountGLAccountPart.withId(
                refBankAccountEntity.getBankAccountGLAccountPart().getId(),
                refBankAccountEntity.getBankAccountGLAccountPart().getCode()
        );
        return RefBankAccount.withId(
                refBankAccountEntity.getId(),
                refBankAccountEntity.getBranchId(),
                refBankAccountEntity.getCode(),
                ValidityPeriod.of(
                        refBankAccountEntity.getStartOfValidity(),
                        refBankAccountEntity.getEndOfValidity()
                ),
                Multilingual.of(refBankAccountEntity.getDescriptions()),
                refBankAccountEntity.getIsPrimaryForGlAccount(),
                bankAccountGLAccountPart
        );
    }

}
