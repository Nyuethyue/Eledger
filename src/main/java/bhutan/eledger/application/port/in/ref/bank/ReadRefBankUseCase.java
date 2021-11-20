package bhutan.eledger.application.port.in.ref.bank;

import bhutan.eledger.domain.ref.bank.RefBank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
@Validated
public interface ReadRefBankUseCase {

    Collection<RefBank> readAll();

    RefBank readById(@NotNull Long id);
}
