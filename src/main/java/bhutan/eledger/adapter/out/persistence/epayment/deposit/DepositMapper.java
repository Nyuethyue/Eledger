package bhutan.eledger.adapter.out.persistence.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.domain.epayment.deposit.DenominationCount;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositReceipt;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Component
public class DepositMapper {

    DepositEntity mapToEntity(CreateDepositUseCase.CreateDepositCommand command) {
        DepositEntity depositEntity = new DepositEntity(
                null,
                command.getPaymentMode(),
                command.getBankDepositDate(),
                null,
                command.getAmount(),
                command.getStatus().getValue(),
                LocalDateTime.now(),
                command.getReceipts().stream().map(r ->
                        new DepositReceiptEntity(null, r.longValue(), null)
                ).collect(Collectors.toUnmodifiableSet()),
                command.getDenominationCounts().stream().map(d ->
                        new DepositDenominationCountsEntity(d.getDenominationId(), d.getDenominationCount())
                ).collect(Collectors.toUnmodifiableSet())
        );

        return depositEntity;
    }

    DepositEntity mapToEntity(Deposit deposit) {
        DepositEntity depositEntity = new DepositEntity(
                null,
                deposit.getPaymentModeId(),
                deposit.getBankDepositDate(),
                null,
                deposit.getAmount(),
                deposit.getStatus().getValue(),
                LocalDateTime.now(),
                deposit.getReceipts().stream().map(r ->
                        new DepositReceiptEntity(deposit.getId(), r.getReceiptId())
                ).collect(Collectors.toUnmodifiableSet()),
                deposit.getDenominationCounts().stream().map(d ->
                        new DepositDenominationCountsEntity(d.getDenominationId(), d.getDenominationCount())
                ).collect(Collectors.toUnmodifiableSet())
        );

        return depositEntity;
    }


    Deposit mapToDomain(DepositEntity depositEntity) {
        return Deposit.withId(
                depositEntity.getId(),
                depositEntity.getPaymentModeId(),
                depositEntity.getAmount(),
                depositEntity.getBankDepositDate(),
                DepositStatus.of(depositEntity.getStatus()),
                depositEntity.getDepositReceipts()
                        .stream()
                        .map(pe ->
                                DepositReceipt.withId(
                                        pe.getId(),
                                        pe.getReceiptId(),
                                        pe.getDeposit().getId()
                                )
                        )
                        .collect(Collectors.toUnmodifiableList()),
                depositEntity.getDepositDenominations()
                        .stream()
                        .map(dd ->
                                DenominationCount.withoutId(
                                        dd.getDenominationId(),
                                        dd.getCount()
                                )
                        )
                        .collect(Collectors.toUnmodifiableList()),
                depositEntity.getLastPrintedDate()

        );
    }
}
