package bhutan.eledger.application.port.in.ref.bankaccount;

import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadRefBankAccountUseCase {
    Collection<RefBankAccount> readAll();

    RefBankAccount readById(@NotNull Long id);

    RefBankAccount readAllByBranchId(@NotNull Long branchId);

    RefBankAccount readByCode(@NotNull String code);

    Long readIdByGlCodeAndFlag(@NotNull String glCode, @NotNull Boolean flag);

    RefBankAccount readPrimaryAccByGlPartFullCode(@NotNull String glPartFullCode);

    Collection<RefBankAccount> readAllByBankIdAndGlPartCode(@Valid ReadRefBankAccountUseCase.ReadBankAccountCommand command);

    @Data
    class ReadBankAccountCommand {
        @NotNull
        private final Long bankId;

        @NotNull
        private final String glPartFullCode;

    }
}
