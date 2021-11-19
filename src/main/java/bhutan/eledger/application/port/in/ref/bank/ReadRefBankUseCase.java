package bhutan.eledger.application.port.in.ref.bank;

import bhutan.eledger.domain.ref.bank.RefBank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface ReadRefBankUseCase {

    Collection<RefBank> readAll();

    RefBank readById(@NotNull Long id);
}
