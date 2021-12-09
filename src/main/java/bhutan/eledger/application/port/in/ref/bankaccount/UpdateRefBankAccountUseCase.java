package bhutan.eledger.application.port.in.ref.bankaccount;

import com.sun.istack.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UpdateRefBankAccountUseCase {
    void updatePrimaryBankAccount(@NotNull Long id);
}
