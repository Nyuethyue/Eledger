package bhutan.eledger.application.port.in.ref.bankaccount;

import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadRefBankAccountUseCase {
    Collection<RefBankAccount> readAll();

    RefBankAccount readById(@NotNull Long id);

    Collection<RefBankAccount> readAllByBranchId(@NotNull Long branchId);

    RefBankAccount readByCode(@NotNull String code);
}
