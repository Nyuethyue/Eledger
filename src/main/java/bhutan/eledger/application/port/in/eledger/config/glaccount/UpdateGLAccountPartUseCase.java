package bhutan.eledger.application.port.in.eledger.config.glaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public interface UpdateGLAccountPartUseCase {

    void updateGLAccountPart(@NotNull Long id, @Valid UpdateGLAccountPartUseCase.UpdateGLAccountPartCommand command);

    @Data
    class UpdateGLAccountPartCommand {
        @NotNull
        @NotEmpty
        private final Map<String, String> descriptions;

        @JsonCreator
        public UpdateGLAccountPartCommand(Map<String, String> descriptions) {
            this.descriptions = descriptions;
        }
    }
}
