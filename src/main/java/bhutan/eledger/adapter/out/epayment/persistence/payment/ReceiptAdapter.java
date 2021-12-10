package bhutan.eledger.adapter.out.epayment.persistence.payment;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ReceiptAdapter implements ReceiptRepositoryPort {
    private final ReceiptEntityRepository receiptEntityRepository;
    private final ReceiptMapper receiptMapper;
    private final RefEntryRepository refEntryRepository;

    @Override
    public Receipt create(Receipt receipt) {
        ReceiptEntity receiptEntity = receiptEntityRepository.save(
                receiptMapper.mapToEntity(receipt)
        );

        RefEntry refCurrencyEntry = refEntryRepository.findByRefNameAndId(RefName.CURRENCY.getValue(), receiptEntity.getRefCurrencyId());
        RefEntry refBankAccountEntry = refEntryRepository.findByRefNameAndId(RefName.BANK_BRANCH.getValue(), receiptEntity.getRefBankBranchId());

        return receiptMapper.mapToDomain(receiptEntity, refCurrencyEntry, refBankAccountEntry);
    }

    @Override
    public void deleteAll() {
        receiptEntityRepository.deleteAll();
    }

    @Override
    public void checkReceipts(Collection<Long> receiptIds) {
        receiptIds
                .forEach(receiptId -> receiptEntityRepository.findById(receiptId).orElseThrow(() ->
                        new RecordNotFoundException("Receipt not found by id:" + receiptId))
                );
    }

    @Override
    public void updateStatuses(ReceiptStatus status, Collection<Long> receiptIds) {
        receiptIds
                .stream()
                .map(receiptEntityRepository::findById)
                .map(Optional::orElseThrow)
                .forEach(r -> {
                    r.setStatus(status.getValue());
                    receiptEntityRepository.save(r);
                });
    }
}
