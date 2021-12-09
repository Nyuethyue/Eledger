package bhutan.eledger.application.port.in.epayment.deposit;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface ImportBankDepositInfoUseCase {

    ImportBankDepositInfoDepositInfo importBankInfo(@Valid ImportBankDepositInfoDepositCommand command);

    @Data
    class ImportBankDepositInfoDepositCommand {
        private final String filePath;
    }

    @Data
    class ImportBankDepositInfoDepositInfo {
        private final Collection<DepositReconciliationInfo> deposits;
    }

    @Data
    class DepositReconciliationInfo {
        private final String depositNumber;
    }
}