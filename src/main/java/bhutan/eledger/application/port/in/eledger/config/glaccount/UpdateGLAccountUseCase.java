package bhutan.eledger.application.port.in.eledger.config.glaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
public interface UpdateGLAccountUseCase {

    void updateGLAccount(@NotNull Long id, @Valid UpdateGLAccountUseCase.UpdateGLAccountCommand command);

    @Data
    class UpdateGLAccountCommand {
        @NotNull
        @NotEmpty
        private final Map<String, String> descriptions;

        @JsonCreator
        public UpdateGLAccountCommand(Map<String, String> descriptions) {
            this.descriptions = descriptions;
        }
    }
}
