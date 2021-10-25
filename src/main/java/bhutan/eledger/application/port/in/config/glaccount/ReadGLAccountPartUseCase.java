package bhutan.eledger.application.port.in.config.glaccount;

import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadGLAccountPartUseCase {

    GLAccountPart readById(@NotNull Long id);

    Collection<GLAccountPart> readAllByParentId(@NotNull Long parentId);

    Collection<GLAccountPart> readAllByPartTypeId(Integer partTypeId);
}
