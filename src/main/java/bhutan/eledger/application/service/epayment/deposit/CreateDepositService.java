package bhutan.eledger.application.service.epayment.deposit;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.Violation;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositReceipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateDepositService implements CreateDepositUseCase {
    private final DepositNumberGeneratorPort depositNumberGeneratorPort;
    private final DepositRepositoryPort depositRepositoryPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;


    @Override
    public Deposit create(CreateDepositCommand command) {
        log.trace("Generating Deposit by command: {}", command);

        LocalDateTime creationDateTime = LocalDateTime.now();

        String depositNumber = depositNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        var deposit = Deposit.withoutId(
                depositNumber,
                command.getPaymentMode(),
                command.getAmount(),
                command.getBankDepositDate(),
                null,
                command.getReceipts().stream().map(r -> DepositReceipt.withoutId(r.longValue())).collect(Collectors.toUnmodifiableSet()),
                command.getDenominationCounts().stream().map(rc ->
                        bhutan.eledger.domain.epayment.deposit.DenominationCount.withoutId(rc.getDenominationId(), rc.getDenominationCount())
                ).collect(Collectors.toList()),
                null,
                creationDateTime
        );

        log.trace("Creating eledger payment deposit: {}", deposit);

        Deposit result = depositRepositoryPort.create(deposit);

        log.trace("Updating eledger receipt statuses to: {}", ReceiptStatus.PENDING_RECONCILIATION);

        receiptRepositoryPort.checkReceipts(command.getReceipts());

        receiptRepositoryPort.updateStatuses(ReceiptStatus.PENDING_RECONCILIATION, command.getReceipts());
        return result;
    }
}
