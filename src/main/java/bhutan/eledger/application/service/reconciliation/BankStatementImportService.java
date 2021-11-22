package bhutan.eledger.application.service.reconciliation;

import bhutan.eledger.application.port.in.epayment.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.application.port.out.epayment.reconciliation.BankStatementFileParserPort;
import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
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
        return bankStatementFileParserPort.getStatements(command);
    }
}
