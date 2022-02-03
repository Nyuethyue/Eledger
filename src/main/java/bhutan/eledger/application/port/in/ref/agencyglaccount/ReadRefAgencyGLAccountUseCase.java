package bhutan.eledger.application.port.in.ref.agencyglaccount;

import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;

import java.util.Collection;

public interface ReadRefAgencyGLAccountUseCase {
    Collection<RefAgencyGLAccount> readAll();

}
