package bhutan.eledger.application.port.in.eledger.config.glaccount;

import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadGLAccountPartTypeUseCase {

    GLAccountPartType readById(@NotNull Integer id);

    Collection<GLAccountPartType> readAll();
}
