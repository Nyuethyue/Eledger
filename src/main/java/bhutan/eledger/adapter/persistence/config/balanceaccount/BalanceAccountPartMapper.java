package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import bhutan.eledger.domain.config.balanceaccount.ValidityPeriod;
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
                partDomain.getValidityPeriod().getStart(),
                partDomain.getValidityPeriod().getEnd()
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
                ValidityPeriod.of(partEntity.getStartOfValidity(), partEntity.getEndOfValidity()),
                Multilingual.of(partEntity.getDescriptions()),
                partEntity.getBalanceAccountPartTypeId()
        );
    }
}
