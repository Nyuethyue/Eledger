package bhutan.eledger.application.port.in.eledger.config.glaccount;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreateGLAccountUseCase {

    Long create(@Valid CreateGLAccountCommand command);

    @Data
    class CreateGLAccountCommand {
        @NotEmpty
        @Valid
        private final Collection<Long> glAccountPartIds;
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

        @NotEmpty
        private final Map<String, String> descriptions;
    }
}
