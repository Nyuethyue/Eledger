package bhutan.eledger.application.port.in.ref.bankbranch;

import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadRefBankBranchUseCase {
    Collection<RefBankBranch> readAll();

    RefBankBranch readById(@NotNull Long id);

    Collection<RefBankBranch> readAllByBankId(@NotNull Long bankId);
}
