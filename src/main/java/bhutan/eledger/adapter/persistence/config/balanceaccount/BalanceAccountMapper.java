package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import org.springframework.stereotype.Component;

@Component
class BalanceAccountMapper {

    BalanceAccountEntity mapToEntity(BalanceAccount balanceAccount) {
        return new BalanceAccountEntity(
                balanceAccount.getId(),
                balanceAccount.getCode(),
                balanceAccount.getBalanceAccountLastPartId(),
                balanceAccount.getStatus().getValue(),
                balanceAccount.getStartDate(),
                balanceAccount.getEndDate(),
                MultilingualEntity.of(balanceAccount.getDescription())
        );
    }

    BalanceAccount mapToDomain(BalanceAccountEntity balanceAccountEntity) {
        return BalanceAccount.withId(
                balanceAccountEntity.getId(),
                balanceAccountEntity.getCode(),
                balanceAccountEntity.getBalanceAccountLastPartId(),
                BalanceAccountStatus.of(balanceAccountEntity.getStatus()),
                balanceAccountEntity.getStartDate(),
                balanceAccountEntity.getEndDate(),
                Multilingual.copyOf(balanceAccountEntity.getDescription())
        );
    }
}
