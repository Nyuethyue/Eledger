package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.payment.FlatReceipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FlatReceiptLoaderBean {
    private final ReceiptRepositoryPort receiptRepositoryPort;

    public Map<Long, FlatReceipt> loadReceiptIdToFlatReceiptMap(DepositEntity entity) {
        return loadReceiptIdToFlatReceiptMap(Arrays.asList(entity));
    }

    public Map<Long, FlatReceipt> loadReceiptIdToFlatReceiptMap(Collection<DepositEntity> entities) {
        Set<Long> receiptIds = new HashSet<>();
        for(DepositEntity depositEntity : entities) {
            for (DepositReceiptEntity dre : depositEntity.getDepositReceipts()) {
                receiptIds.add(dre.getReceiptId());
            }
        }

        Map<Long, FlatReceipt> result = new HashMap<>();
        if(!receiptIds.isEmpty()) {
            Collection<FlatReceipt> receipts = receiptRepositoryPort.readAllByIds(receiptIds);
            for(FlatReceipt fr : receipts) {
                result.put(fr.getId(), fr);
            }
        }
        return result;
    }
}
