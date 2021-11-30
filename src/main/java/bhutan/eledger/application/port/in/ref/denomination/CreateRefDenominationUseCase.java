package bhutan.eledger.application.port.in.ref.denomination;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
public interface CreateRefDenominationUseCase {

    Long create(@Valid CreateRefDenominationUseCase.CreateDenominationCommand command);

    @Data
    class CreateDenominationCommand {
        @NotNull
        @NotEmpty
        private final String denomination;
//        @NotNull
//        @NotEmpty
//        private final String den;
//        @NotEmpty
//        private final Map<String, String> descriptions;
    }

}
