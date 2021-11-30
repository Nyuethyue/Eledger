package bhutan.eledger.application.port.in.ref.bankbranch;


import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Validated
public interface CreateRefBankBranchUseCase {
    Long create(@Valid CreateRefBankBranchUseCase.CreateBranchCommand command);

    @Data
    class CreateBranchCommand {
        @NotNull
        @NotEmpty
        private final String code;

        private final String branchCode;

        @NotNull
        @NotEmpty
        private final String address;

        @NotNull
        private final LocalDate startOfValidity;

        private final LocalDate endOfValidity;

        @NotNull
        private final Long bankId;

        @NotEmpty
        private final Map<String, String> descriptions;

    }

}
