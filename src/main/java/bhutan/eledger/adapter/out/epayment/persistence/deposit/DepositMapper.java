package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.domain.epayment.deposit.DenominationCount;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositReceipt;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
class DepositMapper {
    DepositEntity mapToEntity(Deposit deposit) {
        DepositEntity depositEntity = new DepositEntity(
                null,
                deposit.getDepositNumber(),
                deposit.getPaymentModeId(),
                deposit.getBankDepositDate(),
                deposit.getAmount(),
                deposit.getStatus().getValue(),
                deposit.getCreationDateTime()
        );

        depositEntity.setDepositDenominations(deposit.getDenominationCounts().stream().map(d ->
                new DepositDenominationCountsEntity(d.getDenominationId(), d.getDenominationCount(), depositEntity)
        ).collect(Collectors.toUnmodifiableSet()));

        depositEntity.setDepositReceipts(deposit.getReceipts().stream().map(r ->
                new DepositReceiptEntity(r.getReceiptId(), depositEntity)
        ).collect(Collectors.toUnmodifiableSet()));

        return depositEntity;
    }

    Deposit mapToDomain(DepositEntity depositEntity) {
        return Deposit.withId(
                depositEntity.getId(),
                depositEntity.getDepositNumber(),
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
                depositEntity.getLastPrintedDate(),
                depositEntity.getCreationDateTime()

        );
    }
}
