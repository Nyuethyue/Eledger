package bhutan.eledger.application.port.in.ref.bankbranch;

import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface ReadRefBankBranchUseCase {
    Collection<RefBankBranch> readAll();

    RefBankBranch readById(@NotNull Long id);
}
