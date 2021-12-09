package bhutan.eledger.application.port.in.epayment.deposit;

import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface UpdateDepositUseCase {

    void setDepositStatusesReconciled(@Valid SetDepositStatusesReconciledCommand command);

    @Data
    class SetDepositStatusesReconciledCommand {
        private final Collection<Long> depositIds;
    }
}