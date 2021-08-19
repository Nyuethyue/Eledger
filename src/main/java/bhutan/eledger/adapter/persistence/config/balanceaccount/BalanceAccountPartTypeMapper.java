package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import org.springframework.stereotype.Component;

@Component
class BalanceAccountPartTypeMapper {

    BalanceAccountPartTypeEntity mapToEntity(BalanceAccountPartType partTypeDomain) {
        return new BalanceAccountPartTypeEntity(
                partTypeDomain.getId(),
                partTypeDomain.getLevel(),
                MultilingualEntity.of(partTypeDomain.getDescription())
        );
    }

    BalanceAccountPartType mapToDomain(BalanceAccountPartTypeEntity partTypeEntity) {
        return BalanceAccountPartType.withId(
                partTypeEntity.getId(),
                partTypeEntity.getLevel(),
                Multilingual.copyOf(partTypeEntity.getDescription())
        );
    }
}
