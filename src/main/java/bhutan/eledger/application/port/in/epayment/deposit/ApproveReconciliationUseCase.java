package bhutan.eledger.application.port.in.epayment.deposit;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface ApproveReconciliationUseCase {

    void approveDepositReconciliation(@Valid ApproveReconciliationUseCase.ApproveDepositReconciliationCommand command);

    @Data
    class ApproveDepositReconciliationCommand {
        private final Collection<String> depositNumbers;
    }
}