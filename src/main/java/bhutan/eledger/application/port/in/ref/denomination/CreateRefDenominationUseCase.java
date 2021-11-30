package bhutan.eledger.application.port.in.ref.denomination;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Validated
public interface CreateRefDenominationUseCase {

    Long create(@Valid CreateRefDenominationUseCase.CreateDenominationCommand command);

    @Data
    class CreateDenominationCommand {
        @NotNull
        @NotEmpty
        private final String value;
    }
}
