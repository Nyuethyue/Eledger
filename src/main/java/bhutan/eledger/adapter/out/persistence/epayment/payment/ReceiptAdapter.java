package bhutan.eledger.adapter.out.persistence.epayment.payment;

import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ReceiptAdapter implements ReceiptRepositoryPort {
    private final ReceiptEntityRepository receiptEntityRepository;

    @Override
    public void setStatuses(ReceiptStatus status, Collection<Long> receiptIds) {
        List<ReceiptEntity> res = new LinkedList<>();
        for(Long receiptId : receiptIds) {
            Optional<ReceiptEntity> re = receiptEntityRepository.findById(receiptId);
            if(re.isPresent()) {
                ReceiptEntity receipt = re.get();
                receipt.setStatus(status.getValue());
                res.add(receipt);
            }
        }
        if(!res.isEmpty()) {
            receiptEntityRepository.saveAll(res);
        }
    }
}
