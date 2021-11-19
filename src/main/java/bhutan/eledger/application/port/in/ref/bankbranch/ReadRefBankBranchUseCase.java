package bhutan.eledger.application.port.in.ref.bankbranch;

import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;

import java.util.Collection;

public interface ReadRefBankBranchUseCase {
    Collection<RefBankBranch> readAll();
}
