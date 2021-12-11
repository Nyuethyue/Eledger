package bhutan.eledger.application.service.epayment.deposit;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.application.port.out.ref.paymentmode.PaymentModeRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.DenominationCount;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositReceipt;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateDepositService implements CreateDepositUseCase {
    private final DepositNumberGeneratorPort depositNumberGeneratorPort;
    private final DepositRepositoryPort depositRepositoryPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final PaymentModeRepositoryPort paymentModeRepositoryPort;

    @Override
    public Deposit create(CreateDepositCommand command) {
        log.trace("Generating Deposit by command: {}", command);

        long paymentModeId;
        // @TODO refactor
        if (null != command.getPaymentModeCode()) {
            paymentModeId = paymentModeRepositoryPort.getIdByCode(command.getPaymentModeCode());
            if (PaymentMode.CASH.getValue().equals(command.getPaymentModeCode()) &&
                    (null == command.getDenominationCounts() || command.getDenominationCounts().isEmpty())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation(
                                        "deposit.cash",
                                        "Missing denomination info for CASH deposit"
                                )
                );
            }
        } else {
            paymentModeId = command.getPaymentMode();
        }

        LocalDateTime creationDateTime = LocalDateTime.now();

        String depositNumber = depositNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        Collection<DenominationCount> denominations;
        if(null != command.getDenominationCounts() && !command.getDenominationCounts().isEmpty()) {
            denominations = command.getDenominationCounts().stream().map(rc ->
                    DenominationCount.withoutId(rc.getDenominationId(), rc.getDenominationCount())).collect(Collectors.toList());
        } else {
            denominations = null;
        }
        var deposit = Deposit.withoutId(
                depositNumber,
                paymentModeId,
                command.getAmount(),
                command.getBankDepositDate(),
                DepositStatus.PENDING_RECONCILIATION,
                command.getReceipts().stream().map(r -> DepositReceipt.withoutId(r.longValue())).collect(Collectors.toUnmodifiableSet()),
                denominations,
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

    @Override
    public Collection<Deposit> create(CreateDepositMultipleCommand command) {
        log.trace("Generating Deposit Multiple by command: {}", command);
        return command.getDeposits()
                .stream()
                .map(this::create)
                .collect(Collectors.toUnmodifiableSet());
    }
}
