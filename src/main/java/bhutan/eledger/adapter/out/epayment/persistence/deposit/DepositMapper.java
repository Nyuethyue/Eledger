package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.domain.epayment.deposit.*;
import bhutan.eledger.domain.epayment.payment.FlatReceipt;
import org.springframework.stereotype.Component;

import java.util.*;
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
        Set<DepositDenominationCountsEntity> denominationCountEntities;
        if(null != deposit.getDenominationCounts() && !deposit.getDenominationCounts().isEmpty()) {
            denominationCountEntities = deposit.getDenominationCounts().stream().map(d ->
                    new DepositDenominationCountsEntity(d.getDenominationId(), d.getDenominationCount(), depositEntity)
            ).collect(Collectors.toUnmodifiableSet());
        } else {
            denominationCountEntities = null;
        }
        depositEntity.setDepositDenominations(denominationCountEntities);

        depositEntity.setDepositReceipts(deposit.getReceipts().stream().map(r ->
                new DepositReceiptEntity(r.getReceiptId(), depositEntity)
        ).collect(Collectors.toUnmodifiableSet()));

        return depositEntity;
    }

    Deposit mapToDomain(DepositEntity depositEntity, Map<Long, FlatReceipt> receiptIdToFlatReceipt) {
        List<DenominationCount> denominationCounts;
        if(null != depositEntity.getDepositDenominations()) {
            denominationCounts = depositEntity.getDepositDenominations()
                    .stream()
                    .map(dd ->
                            DenominationCount.withoutId(
                                    dd.getDenominationId(),
                                    dd.getCount()
                            )
                    )
                    .collect(Collectors.toUnmodifiableList());
        } else {
            denominationCounts = null;
        }
        return Deposit.withId(
                depositEntity.getId(),
                depositEntity.getDepositNumber(),
                depositEntity.getPaymentModeId(),
                depositEntity.getAmount(),
                depositEntity.getBankDepositDate(),
                DepositStatus.of(depositEntity.getStatus()),
                depositEntity.getDepositReceipts()
                        .stream()
                        .map(pe -> {
                            FlatReceipt drw = receiptIdToFlatReceipt.get(pe.getReceiptId());
                            return DepositReceipt.withId(
                                            pe.getId(),
                                            pe.getReceiptId(),
                                            pe.getDeposit().getId(),
                                            drw
                                    );
                                }
                        )
                        .collect(Collectors.toUnmodifiableList()),
                denominationCounts,
                depositEntity.getLastPrintedDate(),
                depositEntity.getCreationDateTime()
        );
    }
}
