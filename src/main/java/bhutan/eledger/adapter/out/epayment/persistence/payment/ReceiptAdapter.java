package bhutan.eledger.adapter.out.epayment.persistence.payment;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class ReceiptAdapter implements ReceiptRepositoryPort {
    private final ReceiptEntityRepository receiptEntityRepository;

    @Override
    public void checkReceipts(Collection<Long> receiptIds) {
        receiptIds.stream().forEach(receiptId -> receiptEntityRepository.findById(receiptId).orElseThrow(() ->
                new RecordNotFoundException("Receipt not found by id:" + receiptId)));
    }

    @Override
    public void updateStatuses(ReceiptStatus status, Collection<Long> receiptIds) {
        var receiptEntities = receiptIds
                .stream()
                .map(receiptEntityRepository::findById)
                .map(Optional::orElseThrow)
                .map(r -> r.toBuilder().status(status.getValue()).build())
                .collect(Collectors.toSet());

        receiptEntityRepository.saveAll(receiptEntities);
    }
}
