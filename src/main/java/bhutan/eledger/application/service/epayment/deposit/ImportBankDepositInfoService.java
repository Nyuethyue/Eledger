package bhutan.eledger.application.service.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.ImportBankDepositInfoUseCase;
import bhutan.eledger.application.port.in.epayment.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class ImportBankDepositInfoService implements ImportBankDepositInfoUseCase {
    private final DepositRepositoryPort depositRepositoryPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final BankStatementImportUseCase bankStatementImportUseCase;

    @Override
    public ImportBankDepositInfoDepositInfo importBankInfo(@Valid ImportBankDepositInfoDepositCommand command) {
        List<BankStatementImportReconciliationInfo> infoList = bankStatementImportUseCase.importStatements(
                new BankStatementImportUseCase.ImportBankStatementsCommand(command.getFilePath()));

        infoList.stream().forEach(info -> {
            if(mightBeUsefull(info)) {
                String depositNumber = info.getDepositNumber();
                var deposit = depositRepositoryPort.readByDepositNumber(depositNumber);
                if(deposit.isPresent()) {

                }
            }
        });
        return null;
    }

    private boolean mightBeUsefull(BankStatementImportReconciliationInfo info) {
        return true;
    }
}
