package bhutan.eledger.adapter.out.persistence.epayment.payment;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptSearchPort;
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
class ReceiptSearchAdapter implements ReceiptSearchPort {
    private final ReceiptEntityRepository receiptEntityRepository;
    private final CashReceiptMapper cashReceiptMapper;
    private final RefEntryRepository refEntryRepository;

    @Override
    public SearchResult<Receipt> search(ReceiptCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<Receipt> page = receiptEntityRepository.findAll(
                        querydsl -> resolveQuery(command, querydsl),
                        pageable
                )
                //todo create fill receipt domain and map all data
                .map(receiptEntity -> {
                    RefEntry refEntry = refEntryRepository.findByRefNameAndId(RefName.CURRENCY.getValue(), receiptEntity.getRefCurrencyId());

                    return cashReceiptMapper.mapToDomain(receiptEntity, refEntry);
                });

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<ReceiptEntity> resolveQuery(ReceiptCommand command, Querydsl querydsl) {
        var qReceiptEntity = QReceiptEntity.receiptEntity;

        var jpqlQuery = querydsl
                .createQuery(qReceiptEntity)
                .select(qReceiptEntity);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getRefCurrencyId() != null) {
            predicate.and(qReceiptEntity.refCurrencyId.eq(command.getRefCurrencyId()));
        }

        if (command.getPaymentMode() != null) {
            predicate.and(qReceiptEntity.paymentMode.eq(command.getPaymentMode().getValue()));
        }

        if (command.getReceiptDate() != null) {
            LocalDateTime margin = command.getReceiptDate().plusDays(1).atStartOfDay();
            predicate.and(qReceiptEntity.creationDateTime.before(margin));
        }

        if (command.getGlAccountPartFullCode() != null) {
// todo for future to check performance this way can be used instead joins which will generate subselect instead of distinct and join
//  predicate.and(qReceiptEntity.payments.any().glAccount.code.startsWith(command.getGlAccountPartFullCode()));

            jpqlQuery.innerJoin(QPaymentEntity.paymentEntity)
                    .on(qReceiptEntity.id.eq(QPaymentEntity.paymentEntity.receipt.id))
                    .innerJoin(QPaymentEntity.paymentEntity.glAccount)
                    .distinct();

            predicate.and(QPaymentEntity.paymentEntity.glAccount.code.startsWith(command.getGlAccountPartFullCode()));
        }

        return jpqlQuery.where(predicate);
    }

}
