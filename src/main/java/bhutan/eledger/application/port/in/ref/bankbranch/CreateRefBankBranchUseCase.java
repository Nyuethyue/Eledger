package bhutan.eledger.application.port.in.ref.bankbranch;


import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

public interface CreateRefBankBranchUseCase {
    Long create(@Valid CreateRefBankBranchUseCase.CreateBranchCommand command);

    @Data
    class CreateBranchCommand {
        @NotNull
        @NotEmpty
        @Pattern(regexp="\"[0-9]+\"")
        private final String code;
        @NotNull
        @NotEmpty
        private final String bfscCode;
        @NotNull
        @NotEmpty
        private final String address;
        @NotNull
        @NotEmpty
        private final Long bankId;
        @NotEmpty
        private final Map<String, String> description;

    }

}
