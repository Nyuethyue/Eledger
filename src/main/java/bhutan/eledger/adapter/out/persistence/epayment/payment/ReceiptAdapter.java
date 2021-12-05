package bhutan.eledger.adapter.out.persistence.epayment.payment;

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
    public void updateStatuses(ReceiptStatus status, Collection<Long> receiptIds) {
        var receiptEntities = receiptIds
                .stream()
                .map(receiptEntityRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toSet());

        receiptEntityRepository.saveAll(receiptEntities);
    }
}
