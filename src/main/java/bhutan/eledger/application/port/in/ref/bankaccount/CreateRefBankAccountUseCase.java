package bhutan.eledger.application.port.in.ref.bankaccount;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Map;

@Validated
public interface CreateRefBankAccountUseCase {
    Long create(@Valid CreateRefBankAccountUseCase.CreateBankAccountCommand command);

    @Data
    class CreateBankAccountCommand {
        @NotNull
        private final Long branchId;

        @NotNull
        @NotEmpty
        @Pattern(regexp = "\\d+",message = "Account number accept only numbers.")
        private final String accNumber;

        @NotNull
        private final LocalDate startOfValidity;

        @NotEmpty
        private final Map<String, String> descriptions;

    }
}
