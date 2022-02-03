package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;


import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptForDetailsSearchPort;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
class RefTaxPeriodAdapter implements RefTaxPeriodRepositoryPort {

    private final RefTaxPeriodMapper refTaxPeriodMapper;
    private final RefTaxPeriodEntityRepository refTaxPeriodEntityRepository;

    @Override
    public Long create(RefTaxPeriodConfig bean) {
        return null;
    }

    @Override
    public Optional<RefTaxPeriodConfig> readBy(String taxTypeCode, Integer calendarYear, Long taxPeriodTypeId, Long transactionTypeId) {
        var result = refTaxPeriodEntityRepository.readBy(taxTypeCode, calendarYear, taxPeriodTypeId, transactionTypeId);
        if(result.isPresent()) {
            return Optional.of(refTaxPeriodMapper.mapToDomain(result.get()));
        } else {
            return Optional.empty();
        }
    }

//    public SearchResult<Receipt> search(ReceiptForDetailsSearchPort.ReceiptForDetailsCommand command) {
//        Pageable pageable = PageableResolver.resolve(command);
//
//        Page<Receipt> page = receiptEntityRepository.findAll(
//                querydsl -> resolveQuery(command, querydsl),
//                pageable
//        )
//                .map(receiptEntity -> {
//                    RefEntry refCurrencyEntry = refEntryRepository.findByRefNameAndId(RefName.CURRENCY.getValue(), receiptEntity.getRefCurrencyId());
//                    RefEntry refBankAccountEntry = refEntryRepository.findByRefNameAndId(RefName.BANK_BRANCH.getValue(), receiptEntity.getRefBankBranchId());
//                    RefEntry refIssuingBankAccountEntry = refEntryRepository.findByRefNameAndId(RefName.BANK_BRANCH.getValue(), receiptEntity.getRefIssuingBankBranchId());
//
//                    return cashReceiptMapper.mapToDomain(receiptEntity, refCurrencyEntry, refBankAccountEntry,refIssuingBankAccountEntry);
//                });
//
//        return PagedSearchResult.of(page);
//    }


    @Override
    public void deleteAll() {

    }
}
