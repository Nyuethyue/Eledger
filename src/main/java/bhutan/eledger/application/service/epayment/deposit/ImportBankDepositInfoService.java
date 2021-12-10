package bhutan.eledger.application.service.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.GenerateReconciliationInfoUseCase;
import bhutan.eledger.application.port.in.epayment.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class ImportBankDepositInfoService implements GenerateReconciliationInfoUseCase {
    private final DepositRepositoryPort depositRepositoryPort;
    private final BankStatementImportUseCase bankStatementImportUseCase;

    @Override
    public ReconciliationInfo generate(@Valid GenerateDepositReconciliationInfoCommand command) {
        List<BankStatementImportReconciliationInfo> bankInfoList = bankStatementImportUseCase.importStatements(
                new BankStatementImportUseCase.ImportBankStatementsCommand(command.getFilePath()));

        List<ErrorRecordsInfo> errorRecords = new LinkedList<>();


        List<DepositReconciliationInfo> depositInfoList = new LinkedList<>();
        bankInfoList.stream().forEach(info -> {
            if(allFieldsArPresent(info)) {
                String depositNumber = info.getDepositNumber();
                var dep = depositRepositoryPort.readByDepositNumber(depositNumber);
                if(dep.isPresent()) {
                    var deposit = dep.get();
                    depositInfoList.add(new DepositReconciliationInfo(
                        info.getTransactionId(),
                        info.getBankBranchCode(),
                        info.getPaymentDate(),
                        info.getAmount(),
                        depositNumber,
                        deposit.getCreationDateTime(),
                        deposit.getAmount()
                    ));
                } else {
                    errorRecords.add(mapTo(info));
                }
            } else {
                errorRecords.add(mapTo(info));
            }
        });
        return new ReconciliationInfo(errorRecords.isEmpty(), depositInfoList, errorRecords);
    }

    private ErrorRecordsInfo mapTo(BankStatementImportReconciliationInfo record) {
        return new ErrorRecordsInfo(
                record.getTransactionId(),
                record.getBankBranchCode(),
                record.getDepositNumber(),
                record.getPaymentDate(),
                record.getAmount()
        );
    }

    private boolean allFieldsArPresent(BankStatementImportReconciliationInfo info) {
        return null != info.getDepositNumber() && !info.getDepositNumber().isEmpty() &&
                null != info.getBankBranchCode() && !info.getBankBranchCode().isEmpty() &&
                null != info.getTransactionId() && !info.getTransactionId().isEmpty() &&
                null != info.getPaymentDate() &&
                null != info.getAmount();
    }
}
