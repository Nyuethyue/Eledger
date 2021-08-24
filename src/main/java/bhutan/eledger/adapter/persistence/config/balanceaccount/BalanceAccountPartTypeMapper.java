package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import org.springframework.stereotype.Component;

@Component
class BalanceAccountPartTypeMapper {

    BalanceAccountPartTypeEntity mapToEntity(BalanceAccountPartType partTypeDomain) {
        BalanceAccountPartTypeEntity balanceAccountPartTypeEntity =
                new BalanceAccountPartTypeEntity(
                        partTypeDomain.getId(),
                        partTypeDomain.getLevel(),
                        partTypeDomain.getCreationDateTime()
                );

        partTypeDomain.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new BalanceAccountPartTypeDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(balanceAccountPartTypeEntity::addToDescriptions);

        return balanceAccountPartTypeEntity;
    }

    BalanceAccountPartType mapToDomain(BalanceAccountPartTypeEntity partTypeEntity) {
        return BalanceAccountPartType.withId(
                partTypeEntity.getId(),
                partTypeEntity.getLevel(),
                partTypeEntity.getCreationDateTime(),
                Multilingual.of(partTypeEntity.getDescriptions())
        );
    }
}
