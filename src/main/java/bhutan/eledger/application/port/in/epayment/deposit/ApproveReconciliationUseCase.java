package bhutan.eledger.application.port.in.epayment.deposit;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ApproveReconciliationUseCase {

    void approveDepositReconciliation(@Valid ApproveReconciliationUseCase.ApproveDepositReconciliationCommand command);

    @Getter
    @ToString
    class ApproveDepositReconciliationCommand {
        @Valid
        @NotNull
        @NotEmpty
        private final Collection<String> depositNumbers;

        @JsonCreator
        public ApproveDepositReconciliationCommand(Collection<String> depositNumbers) {
            this.depositNumbers = depositNumbers;
        }
    }
}