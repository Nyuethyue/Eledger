package bhutan.eledger.application.port.in.eledger.config.glaccount;

import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreateGLAccountPartUseCase {

    Collection<GLAccountPart> create(@Valid CreateGLAccountPartUseCase.CreateGLAccountPartCommand command);

    @Data
    class CreateGLAccountPartCommand {
        private final Long parentId;
        @NotNull
        @Positive
        private final Integer glAccountPartTypeId;

        @NotNull
        @Valid
        private final Collection<GLAccountPartCommand> glAccountParts;
    }

    @Data
    class GLAccountPartCommand {
        @NotNull
        private final String code;
        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
