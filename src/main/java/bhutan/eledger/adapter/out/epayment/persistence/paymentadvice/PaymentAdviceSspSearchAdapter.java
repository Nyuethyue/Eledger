package bhutan.eledger.adapter.out.epayment.persistence.paymentadvice;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceSspPort;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PaymentAdviceSspSearchAdapter implements SearchPaymentAdviceSspPort {
    private final PaymentAdviceEntityRepository paymentAdviceEntityRepository;
    private final PaymentAdviceMapper paymentAdviceMapper;

    @Override
    public SearchResult<PaymentAdvice> search(PaymentAdviseSspSearchCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<PaymentAdvice> page = paymentAdviceEntityRepository.findAll(
                querydsl -> resolveQuery(command, querydsl),
                pageable
        ).map(paymentAdviceMapper::mapToDomain);

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<PaymentAdviceEntity> resolveQuery(PaymentAdviseSspSearchCommand command, Querydsl querydsl) {
        var qPaymentAdvice = QPaymentAdviceEntity.paymentAdviceEntity;

        var jpqlQuery = querydsl
                .createQuery(qPaymentAdvice)
                .select(qPaymentAdvice);

        BooleanBuilder predicate = new BooleanBuilder();

        jpqlQuery = jpqlQuery.innerJoin(qPaymentAdvice.taxpayer);

        predicate.and(qPaymentAdvice.taxpayer.tpn.eq(command.getTpn()));

        if (command.getPan() != null) {
            predicate.and(qPaymentAdvice.pan.eq(command.getPan()));
        } else {
            if (command.getStatus() != null) {
                predicate.and(qPaymentAdvice.status.eq(command.getStatus().getValue()));
            }

            if (command.getTotalToBePaidAmountFrom() != null) {
                predicate.and(qPaymentAdvice.totalToBePaidAmount.goe(command.getTotalToBePaidAmountFrom()));
            }

            if (command.getTotalToBePaidAmountTo() != null) {
                predicate.and(qPaymentAdvice.totalToBePaidAmount.loe(command.getTotalToBePaidAmountTo()));
            }

            if (command.getCreationDateTimeFrom() != null) {
                predicate.and(qPaymentAdvice.creationDateTime.goe(command.getCreationDateTimeFrom()));
            }

            if (command.getCreationDateTimeTo() != null) {
                predicate.and(qPaymentAdvice.creationDateTime.goe(command.getCreationDateTimeTo()));
            }

        }

        return jpqlQuery.where(predicate);
    }
}
