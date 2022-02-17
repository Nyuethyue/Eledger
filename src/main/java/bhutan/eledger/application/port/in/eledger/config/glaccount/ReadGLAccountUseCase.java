package bhutan.eledger.application.port.in.eledger.config.glaccount;

import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ReadGLAccountUseCase {
    Optional<GLAccount> readByCode(@NotNull String code);
}
