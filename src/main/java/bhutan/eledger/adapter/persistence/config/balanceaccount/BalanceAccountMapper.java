package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import bhutan.eledger.domain.config.balanceaccount.ValidityPeriod;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class BalanceAccountMapper {

    BalanceAccountEntity mapToEntity(BalanceAccount balanceAccount) {
        BalanceAccountEntity balanceAccountEntity = new BalanceAccountEntity(
                balanceAccount.getId(),
                balanceAccount.getCode(),
                balanceAccount.getBalanceAccountLastPartId(),
                balanceAccount.getStatus().getValue(),
                balanceAccount.getCreationDateTime(),
                balanceAccount.getLastModificationDateTime(),
                balanceAccount.getValidityPeriod().getStart(),
                balanceAccount.getValidityPeriod().getEnd()
        );

        balanceAccountEntity.setDescriptions(
                balanceAccount.getDescription()
                        .getTranslations()
                        .stream()
                        .map(t ->
                                new BalanceAccountDescriptionEntity(
                                        t.getId(),
                                        t.getLanguageCode(),
                                        t.getValue(),
                                        balanceAccountEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return balanceAccountEntity;
    }

    BalanceAccount mapToDomain(BalanceAccountEntity balanceAccountEntity) {
        return BalanceAccount.withId(
                balanceAccountEntity.getId(),
                balanceAccountEntity.getCode(),
                BalanceAccountStatus.of(balanceAccountEntity.getStatus()),
                balanceAccountEntity.getCreationDateTime(),
                balanceAccountEntity.getLastModificationDateTime(),
                ValidityPeriod.of(balanceAccountEntity.getStartOfValidity(), balanceAccountEntity.getEndOfValidity()),
                Multilingual.of(balanceAccountEntity.getDescriptions()),
                balanceAccountEntity.getBalanceAccountLastPartId()
        );
    }
}
