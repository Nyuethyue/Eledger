package bhutan.eledger.application.service.epayment.deposit.reconciliation;

import bhutan.eledger.application.port.in.epayment.payment.deposit.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.reconciliation.BankStatementFileParserPort;
import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class BankStatementImportService implements BankStatementImportUseCase {
    private final BankStatementFileParserPort bankStatementFileParserPort;

    @Override
    public List<BankStatementImportReconciliationInfo> importStatements(ImportBankStatementsCommand command) {
        return bankStatementFileParserPort.getStatements(command.getExcelFilePath());
    }
}
