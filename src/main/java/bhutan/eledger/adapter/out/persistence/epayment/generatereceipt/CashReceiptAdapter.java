package bhutan.eledger.adapter.out.persistence.epayment.generatereceipt;

import bhutan.eledger.application.port.out.epayment.generatereceipt.CashReceiptRepositoryPort;
import bhutan.eledger.domain.epayment.generatereceipt.CashReceipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CashReceiptAdapter implements CashReceiptRepositoryPort {
    private final CashReceiptMapper cashReceiptMapper;
    private final ReceiptEntityRepository receiptEntityRepository;

    @Override
    public CashReceipt create(CashReceipt cashReceipt) {
        ReceiptEntity receiptEntity = receiptEntityRepository.save(
                cashReceiptMapper.mapToEntity(cashReceipt)
        );

        return cashReceiptMapper.mapToDomain(receiptEntity);
    }

    @Override
    public void deleteAll() {
        receiptEntityRepository.deleteAll();
    }
}
