package bhutan.eledger.application.port.in.config.glaccount;

import bhutan.eledger.domain.config.glaccount.GLAccountPartType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadGLAccountPartTypeUseCase {

    GLAccountPartType readById(@NotNull Integer id);

    Collection<GLAccountPartType> readAll();
}
