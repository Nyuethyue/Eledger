package bhutan.eledger.adapter.out.epayment.persistence.payment;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptForDetailsSearchPort;
import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.Receipt;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class ReceiptForDetailsSearchAdapter implements ReceiptForDetailsSearchPort {
    private final ReceiptEntityRepository receiptEntityRepository;
    private final ReceiptMapper cashReceiptMapper;
    private final RefEntryRepository refEntryRepository;


    @Override
    public SearchResult<Receipt> search(ReceiptForDetailsCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<Receipt> page = receiptEntityRepository.findAll(
                        querydsl -> resolveQuery(command, querydsl),
                        pageable
                )
                //todo create fill receipt domain and map all data
                .map(receiptEntity -> {
                    RefEntry refCurrencyEntry = refEntryRepository.findByRefNameAndId(RefName.CURRENCY.getValue(), receiptEntity.getRefCurrencyId());
                    RefEntry refBankAccountEntry = refEntryRepository.findByRefNameAndId(RefName.BANK_BRANCH.getValue(), receiptEntity.getRefBankBranchId());

                    return cashReceiptMapper.mapToDomain(receiptEntity, refCurrencyEntry, refBankAccountEntry);
                });

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<ReceiptEntity> resolveQuery(ReceiptForDetailsCommand command, Querydsl querydsl) {
        var qReceiptEntity = QReceiptEntity.receiptEntity;

        var jpqlQuery = querydsl
                .createQuery(qReceiptEntity)
                .select(qReceiptEntity);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getTpn() != null) {
            jpqlQuery = jpqlQuery.innerJoin(qReceiptEntity.taxpayer);
            predicate.and(qReceiptEntity.taxpayer.tpn.eq(command.getTpn()));
        }

        if (command.getReceiptNumber() != null) {
            predicate.and(qReceiptEntity.receiptNumber.eq(command.getReceiptNumber()));
        }

        if (command.getOtherReferenceNumber() != null) {
            predicate.and(qReceiptEntity.otherReferenceNumber.eq(command.getOtherReferenceNumber()));
        }

        if (command.getLiabilityFromDate() != null) {
            LocalDateTime margin = command.getLiabilityFromDate().atStartOfDay();
            predicate.and(qReceiptEntity.creationDateTime.after(margin));
        }

        if (command.getLiabilityToDate() != null) {
            LocalDateTime margin = command.getLiabilityToDate().plusDays(1).atStartOfDay();
            predicate.and(qReceiptEntity.creationDateTime.before(margin));
        }

        if (command.getReceiptFromAmount() != null) {
            predicate.and(qReceiptEntity.totalPaidAmount.goe(command.getReceiptFromAmount()));
        }

        if (command.getReceiptToAmount() != null) {
            predicate.and(qReceiptEntity.totalPaidAmount.loe(command.getReceiptToAmount()));
        }

        return jpqlQuery.where(predicate);
    }
}
