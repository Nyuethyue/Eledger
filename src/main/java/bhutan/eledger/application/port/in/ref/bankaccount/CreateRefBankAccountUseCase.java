package bhutan.eledger.application.port.in.ref.bankaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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
        @Pattern(regexp = "\\d+", message = "Account number accept only numbers.")
        private final String code;

        @NotNull
        private final LocalDate startOfValidity;

        private final LocalDate endOfValidity;

        @NotNull
        private final Boolean isPrimaryForGlAccount;

        @NotEmpty
        private final Map<String, String> descriptions;

        @Valid
        @NotNull
        private final BankAccountGLAccountPartCommand bankAccountGLAccountPart;

    }

    @Data
    class BankAccountGLAccountPartCommand {
        @NotNull
        @NotEmpty
        private String code;

        @JsonCreator
        public BankAccountGLAccountPartCommand(@JsonProperty("code") String code) {

            this.code = code;
        }

    }
}
