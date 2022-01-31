package bhutan.eledger.application.port.out.ref.agencyglaccount;

import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;

import java.util.Collection;

public interface RefAgencyGLAccountRepositoryPort {

    Collection<RefAgencyGLAccount> create(Collection<RefAgencyGLAccount> agencyGLAccounts);

}
