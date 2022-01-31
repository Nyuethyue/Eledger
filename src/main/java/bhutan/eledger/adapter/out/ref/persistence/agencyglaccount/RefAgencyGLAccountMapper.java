package bhutan.eledger.adapter.out.ref.persistence.agencyglaccount;

import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import org.springframework.stereotype.Component;

@Component
class RefAgencyGLAccountMapper {
    RefAgencyGLAccountEntity mapToEntity(RefAgencyGLAccount refAgencyGLAccount) {

        RefAgencyGLAccountEntity refAgencyGLAccountEntity = new RefAgencyGLAccountEntity(
                refAgencyGLAccount.getId(),
                refAgencyGLAccount.getCode(),
                refAgencyGLAccount.getAgencyId()
        );


        return refAgencyGLAccountEntity;
    }

    RefAgencyGLAccount mapToDomain(RefAgencyGLAccountEntity refAgencyGLAccountEntity) {
        return RefAgencyGLAccount.withId(
                refAgencyGLAccountEntity.getId(),
                refAgencyGLAccountEntity.getCode(),
                refAgencyGLAccountEntity.getAgencyId()
        );
    }
}
