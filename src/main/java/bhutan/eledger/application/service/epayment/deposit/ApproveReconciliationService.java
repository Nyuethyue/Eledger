package bhutan.eledger.application.service.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.ApproveReconciliationUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositReceipt;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class ApproveReconciliationService implements ApproveReconciliationUseCase {
    private final DepositRepositoryPort depositRepositoryPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;

    @Override
    public void approveDepositReconciliation(@Valid ApproveReconciliationUseCase.ApproveDepositReconciliationCommand command) {
        command.getDepositNumbers().stream().forEach(dn -> {
            Deposit deposit = depositRepositoryPort.requiredReadByDepositNumber(dn);
            depositRepositoryPort.updateStatus(deposit.getId(), DepositStatus.RECONCILED);
            if(null != deposit.getReceipts() && !deposit.getReceipts().isEmpty()) {
                receiptRepositoryPort.updateStatuses(
                        ReceiptStatus.RECONCILED,
                        deposit.getReceipts().stream().map(DepositReceipt::getReceiptId).collect(Collectors.toUnmodifiableSet()));
            }
        });
    }
}
