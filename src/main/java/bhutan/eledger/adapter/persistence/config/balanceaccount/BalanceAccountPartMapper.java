package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import org.springframework.stereotype.Component;

@Component
class BalanceAccountPartMapper {

    BalanceAccountPartEntity mapToEntity(BalanceAccountPart partDomain) {
        return new BalanceAccountPartEntity(
                partDomain.getId(),
                partDomain.getCode(),
                partDomain.getParentId(),
                partDomain.getBalanceAccountPartLevelId(),
                partDomain.getStatus().getValue(),
                partDomain.getStartDate(),
                partDomain.getEndDate(),
                MultilingualEntity.of(partDomain.getDescription())
        );
    }

    BalanceAccountPart mapToDomain(BalanceAccountPartEntity partEntity) {
        return BalanceAccountPart.withId(
                partEntity.getId(),
                partEntity.getCode(),
                partEntity.getParentId(),
                partEntity.getBalanceAccountPartTypeId(),
                BalanceAccountPartStatus.of(partEntity.getStatus()),
                partEntity.getStartDate(),
                partEntity.getEndDate(),
                Multilingual.copyOf(partEntity.getDescription())
        );
    }
}
