package bhutan.eledger.adapter.out.epayment.persistence.payment;

import bhutan.eledger.application.port.out.epayment.payment.CashReceiptRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.CashReceipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CashReceiptAdapter implements CashReceiptRepositoryPort {
    private final CashReceiptMapper cashReceiptMapper;
    private final ReceiptEntityRepository receiptEntityRepository;
    private final RefEntryRepository refEntryRepository;

    @Override
    public CashReceipt create(CashReceipt cashReceipt) {
        ReceiptEntity receiptEntity = receiptEntityRepository.save(
                cashReceiptMapper.mapToEntity(cashReceipt)
        );

        RefEntry refEntry = refEntryRepository.findByRefNameAndId(RefName.CURRENCY.getValue(), receiptEntity.getRefCurrencyId());

        return cashReceiptMapper.mapToDomain(receiptEntity, refEntry);
    }

    @Override
    public void deleteAll() {
        receiptEntityRepository.deleteAll();
    }
}
