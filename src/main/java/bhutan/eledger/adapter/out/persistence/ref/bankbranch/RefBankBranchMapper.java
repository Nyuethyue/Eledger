package bhutan.eledger.adapter.out.persistence.ref.bankbranch;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import org.springframework.stereotype.Component;

@Component
class RefBankBranchMapper {
    RefBankBranchEntity mapToEntity(RefBankBranch refBankBranch) {
        RefBankBranchEntity refBankBranchEntity =
                new RefBankBranchEntity(
                        refBankBranch.getId(),
                        refBankBranch.getCode(),
                        refBankBranch.getBranchCode(),
                        refBankBranch.getAddress(),
                        refBankBranch.getValidityPeriod().getStart(),
                        refBankBranch.getValidityPeriod().getEnd(),
                        refBankBranch.getBankId()
                );

        refBankBranch.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefBankBranchDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refBankBranchEntity::addToDescriptions);

        return refBankBranchEntity;
    }

    RefBankBranch mapToDomain(RefBankBranchEntity refBankBranchEntity) {
        return RefBankBranch.withId(
                refBankBranchEntity.getId(),
                refBankBranchEntity.getCode(),
                refBankBranchEntity.getBranchCode(),
                refBankBranchEntity.getAddress(),
                ValidityPeriod.of(
                        refBankBranchEntity.getStartOfValidity(),
                        refBankBranchEntity.getEndOfValidity()
                ),
                refBankBranchEntity.getBankId(),
                Multilingual.of(refBankBranchEntity.getDescriptions())
        );
    }
}
