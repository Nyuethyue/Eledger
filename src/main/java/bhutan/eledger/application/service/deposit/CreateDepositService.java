package bhutan.eledger.application.service.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositReceipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateDepositService implements CreateDepositUseCase {
    private final DepositRepositoryPort depositRepositoryPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;

    @Override
    public Long create(CreateDepositUseCase.CreateDepositCommand command) {
        log.trace("Generating Deposit by command: {}", command);

        var deposit = Deposit.withoutId(
                command.getPaymentMode(),
                command.getAmount(),
                command.getBankDepositDate(),
                command.getStatus(),
                command.getReceipts().stream().map(r -> DepositReceipt.withoutId(r.longValue())).collect(Collectors.toUnmodifiableSet()),
                command.getDenominationCounts().stream().map(rc ->
                        bhutan.eledger.domain.epayment.deposit.DenominationCount.withoutId(rc.getDenominationId(), rc.getDenominationCount())
                ).collect(Collectors.toList()),
                null
        );

        log.trace("Creating eledger payment deposit: {}", deposit);

        Long depositId = depositRepositoryPort.create(deposit);

        log.trace("Updating eledger receipt statuses to: {}", ReceiptStatus.PRE_RECONCILIATION);

        receiptRepositoryPort.updateStatuses(ReceiptStatus.PRE_RECONCILIATION, command.getReceipts());
        return depositId;
    }
}
