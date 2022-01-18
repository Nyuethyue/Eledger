package bhutan.eledger.application.port.in.eledger.config.glaccount;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
public interface CreateGLAccountUseCase {

    Long create(@Valid CreateGLAccountCommand command);

    @Data
    class CreateGLAccountCommand {
        @NotNull
        @Valid
        private final GLAccountLastPartCommand glAccountLastPart;
        @NotEmpty
        private final Map<String, String> descriptions;
    }


    @Data
    class GLAccountLastPartCommand {
        @NotNull
        private final String code;

        @NotNull
        private final Long parentId;

        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
