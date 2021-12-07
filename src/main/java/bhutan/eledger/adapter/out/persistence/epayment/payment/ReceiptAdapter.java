package bhutan.eledger.adapter.out.persistence.epayment.payment;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.Violation;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class ReceiptAdapter implements ReceiptRepositoryPort {
    private final ReceiptEntityRepository receiptEntityRepository;

    @Override
    public void checkReceipts(Collection<Long> receiptIds) {
        receiptIds.stream().forEach(receiptId -> receiptEntityRepository.findById(receiptId).orElseThrow(() -> new ViolationException(
                new ValidationError(
                        Set.of(
                                new Violation("receipt.id", "Receipt not found by id:" + receiptId)
                        )
                )
        )));
    }

    @Override
    public void updateStatuses(ReceiptStatus status, Collection<Long> receiptIds) {
        var receiptEntities = receiptIds
                .stream()
                .map(receiptEntityRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toSet());

        receiptEntityRepository.saveAll(receiptEntities);
    }
}