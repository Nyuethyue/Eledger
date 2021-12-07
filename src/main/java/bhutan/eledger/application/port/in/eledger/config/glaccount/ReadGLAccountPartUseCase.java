package bhutan.eledger.application.port.in.eledger.config.glaccount;

import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadGLAccountPartUseCase {

    GLAccountPart readById(@NotNull Long id);

    Collection<GLAccountPart> readAllByParentId(@NotNull Long parentId);

    Collection<GLAccountPart> readAllByPartTypeId(@NotNull Integer partTypeId);

    Boolean existsByFullCode(@NotNull String fullCode);
}
