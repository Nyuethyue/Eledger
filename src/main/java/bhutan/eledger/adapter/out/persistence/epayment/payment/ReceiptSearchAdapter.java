package bhutan.eledger.adapter.out.persistence.epayment.payment;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptSearchPort;
import bhutan.eledger.domain.epayment.payment.Receipt;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ReceiptSearchAdapter implements ReceiptSearchPort {
    private final ReceiptEntityRepository receiptEntityRepository;
    private final CashReceiptMapper cashReceiptMapper;

    @Override
    public SearchResult<Receipt> search(ReceiptCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<Receipt> page = receiptEntityRepository.findAll(
                        querydsl -> resolveQuery(command, querydsl),
                        pageable
                )
                //todo create fill receipt domain and map all data
                .map(cashReceiptMapper::mapToDomain);

        return new PagedSearchResult<>(page);
    }

    private JPQLQuery<ReceiptEntity> resolveQuery(ReceiptCommand command, Querydsl querydsl) {
        var qReceiptEntity = QReceiptEntity.receiptEntity;

        var jpqlQuery = querydsl
                .createQuery(qReceiptEntity)
                .select(qReceiptEntity);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getCurrency() != null) {
            predicate.and(qReceiptEntity.currency.eq(command.getCurrency()));
        }

        if (command.getPaymentMode() != null) {
            predicate.and(qReceiptEntity.paymentMode.eq(command.getPaymentMode().getValue()));
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
