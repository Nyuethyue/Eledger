package bhutan.eledger.application.service.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.GenerateReconciliationInfoUseCase;
import bhutan.eledger.application.port.in.epayment.payment.deposit.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
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
        for(BankStatementImportReconciliationInfo info : bankInfoList) {
            if(allMandatoryFieldsArePresent(info)) {
                String depositNumber = info.getDepositNumber();
                var dep = depositRepositoryPort.readByDepositNumber(depositNumber);
                if(dep.isPresent()) {
                    var deposit = dep.get();
                    if(DepositStatus.PENDING_RECONCILIATION.equals( deposit.getStatus())) {
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
                        errorRecords.add(mapTo("WRONG_STATUS", info));
                    }
                } else {
                    errorRecords.add(mapTo("MISSING_DEPOSIT", info));
                }
            } else {
                errorRecords.add(mapTo("MISSING_DATA", info));
            }
        }
        return new ReconciliationInfo(errorRecords.isEmpty(), depositInfoList, errorRecords);
    }

    private ErrorRecordsInfo mapTo(String errorType, BankStatementImportReconciliationInfo record) {
        return new ErrorRecordsInfo(
                errorType,
                record.getTransactionId(),
                record.getBankBranchCode(),
                record.getDepositNumber(),
                record.getPaymentDate(),
                record.getAmount()
        );
    }

    private boolean allMandatoryFieldsArePresent(BankStatementImportReconciliationInfo info) {
        return null != info.getDepositNumber() && !info.getDepositNumber().isEmpty() &&
                null != info.getTransactionId() && !info.getTransactionId().isEmpty() &&
                null != info.getPaymentDate() &&
                null != info.getAmount();
    }
}
