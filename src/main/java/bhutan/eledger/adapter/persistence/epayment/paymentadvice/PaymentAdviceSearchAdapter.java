package bhutan.eledger.adapter.persistence.epayment.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdvicePort;
import bhutan.eledger.domain.epayment.PaymentAdvice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;

import am.iunetworks.lib.multilingual.core.Multilingual;
import am.iunetworks.lib.multilingual.core.Translation;
import org.springframework.stereotype.Component;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class PaymentAdviceSearchAdapter implements SearchPaymentAdvicePort {
    private final PaymentAdviceEntityRepository paymentAdviceEntityRepository;
    private final PaymentAdviceMapper paymentAdviceMapper;

    @Override
    public SearchResult<PaymentAdvice> search(SearchPaymentAdvicePort.PaymentAdviseSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<PaymentAdvice> page = paymentAdviceEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(paymentAdviceMapper::mapToDomain);

        return new PagedSearchResult<>(page);
    }

    private JPQLQuery<PaymentAdviceEntity> resolveQuery(PaymentAdviseSearchCommand command, Querydsl querydsl) {
        var qPaymentAdvice = QPaymentAdviceEntity.paymentAdviceEntity;

        var jpqlQuery = querydsl
                .createQuery(qPaymentAdvice)
                .select(qPaymentAdvice);

        BooleanBuilder predicate = new BooleanBuilder();

        if (command.getPan() != null) {
            predicate.and(qPaymentAdvice.pan.eq(command.getPan()));
        } else
        if (command.getTpn() != null) {
            predicate.and(qPaymentAdvice.tpn.eq(command.getTpn()));
        }

        return jpqlQuery.where(predicate);
    }
}
