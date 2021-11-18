package bhutan.eledger.application.port.in.eledger.config.property;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Validated
public interface UpdatePropertyUseCase {

    @NonNull
    Long update(@NotNull Long id, @NotNull @Valid UpdatePropertyUseCase.UpdatePropertyCommand command);


    @Data
    class UpdatePropertyCommand {

        @Future
        private final LocalDate startOfValidity;

        @NotNull
        private final String value;

        private final Map<String, String> descriptions;
    }
}
