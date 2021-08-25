package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import org.springframework.stereotype.Component;

@Component
class BalanceAccountPartMapper {

    BalanceAccountPartEntity mapToEntity(BalanceAccountPart partDomain) {
        BalanceAccountPartEntity balanceAccountPartEntity = new BalanceAccountPartEntity(
                partDomain.getId(),
                partDomain.getCode(),
                partDomain.getParentId(),
                partDomain.getBalanceAccountPartLevelId(),
                partDomain.getStatus().getValue(),
                partDomain.getCreationDateTime(),
                partDomain.getLastModificationDateTime(),
                partDomain.getStartDate(),
                partDomain.getEndDate()
        );

        partDomain.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new BalanceAccountPartDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(balanceAccountPartEntity::addToDescriptions);

        return balanceAccountPartEntity;
    }

    BalanceAccountPart mapToDomain(BalanceAccountPartEntity partEntity) {
        return BalanceAccountPart.withId(
                partEntity.getId(),
                partEntity.getCode(),
                partEntity.getParentId(),
                BalanceAccountPartStatus.of(partEntity.getStatus()),
                partEntity.getCreationDateTime(),
                partEntity.getLastModificationDateTime(),
                partEntity.getStartDate(),
                partEntity.getEndDate(),
                Multilingual.of(partEntity.getDescriptions()),
                partEntity.getBalanceAccountPartTypeId()
        );
    }
}
