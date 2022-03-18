package bhutan.eledger.adapter.out.ref.persistence.agencyglaccount;

import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import org.springframework.stereotype.Component;

@Component
class RefAgencyGLAccountMapper {
    RefAgencyGLAccountEntity mapToEntity(RefAgencyGLAccount refAgencyGLAccount) {

        RefAgencyGLAccountEntity refAgencyGLAccountEntity = new RefAgencyGLAccountEntity(
                refAgencyGLAccount.getId(),
                refAgencyGLAccount.getCode(),
                refAgencyGLAccount.getAgencyCode(),
                refAgencyGLAccount.getValidityPeriod().getStart(),
                refAgencyGLAccount.getValidityPeriod().getEnd()
        );


        return refAgencyGLAccountEntity;
    }

    RefAgencyGLAccount mapToDomain(RefAgencyGLAccountEntity refAgencyGLAccountEntity) {
        return RefAgencyGLAccount.withId(
                refAgencyGLAccountEntity.getId(),
                refAgencyGLAccountEntity.getCode(),
                refAgencyGLAccountEntity.getAgencyCode(),
                ValidityPeriod.of(
                        refAgencyGLAccountEntity.getStartOfValidity(),
                        refAgencyGLAccountEntity.getEndOfValidity()
                )
        );
    }
}
