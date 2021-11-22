package bhutan.eledger.application.port.in.ref.bankbranch;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Validated
public interface CreateRefBankBranchUseCase {
    Long create(@Valid CreateRefBankBranchUseCase.CreateBranchCommand command);

    @Data
    class CreateBranchCommand {
        @NotNull
        @NotEmpty
        @Pattern(regexp = "\\d+",message = "Branch code accept only numbers.")
        private final String code;
        @NotNull
        @NotEmpty
        private final String bfscCode;
        @NotNull
        @NotEmpty
        private final String address;
        @NotNull
        private final Long bankId;
        @NotEmpty
        private final Map<String, String> descriptions;

    }

}
