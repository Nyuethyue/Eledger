package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import org.springframework.stereotype.Component;

@Component
class BalanceAccountMapper {

    BalanceAccountEntity mapToEntity(BalanceAccount balanceAccount) {
        BalanceAccountEntity balanceAccountEntity = new BalanceAccountEntity(
                balanceAccount.getId(),
                balanceAccount.getCode(),
                balanceAccount.getBalanceAccountLastPartId(),
                balanceAccount.getStatus().getValue(),
                balanceAccount.getStartDate(),
                balanceAccount.getEndDate()
        );

        balanceAccount.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new BalanceAccountDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(balanceAccountEntity::addToDescription);

        return balanceAccountEntity;
    }

    BalanceAccount mapToDomain(BalanceAccountEntity balanceAccountEntity) {
        return BalanceAccount.withId(
                balanceAccountEntity.getId(),
                balanceAccountEntity.getCode(),
                balanceAccountEntity.getBalanceAccountLastPartId(),
                BalanceAccountStatus.of(balanceAccountEntity.getStatus()),
                balanceAccountEntity.getStartDate(),
                balanceAccountEntity.getEndDate(),
                Multilingual.of(balanceAccountEntity.getDescriptions())
        );
    }
}
