package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.payment.FlatReceipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class FlatReceiptLoaderBean {
    private final ReceiptRepositoryPort receiptRepositoryPort;

    public Map<Long, FlatReceipt> loadReceiptIdToFlatReceiptMap(DepositEntity entity) {
        return loadReceiptIdToFlatReceiptMap(List.of(entity));
    }

    public Map<Long, FlatReceipt> loadReceiptIdToFlatReceiptMap(Collection<DepositEntity> entities) {
        Set<Long> receiptIds = entities
                .stream()
                .map(DepositEntity::getDepositReceipts)
                .flatMap(Collection::stream)
                .map(DepositReceiptEntity::getReceiptId)
                .collect(Collectors.toUnmodifiableSet());

        Map<Long, FlatReceipt> result;

        if (receiptIds.isEmpty()) {
            result = Map.of();
        } else {
            result = receiptRepositoryPort.readAllByIds(receiptIds)
                    .stream()
                    .collect(Collectors.toMap(FlatReceipt::getId, Function.identity()));
        }

        return result;
    }
}
