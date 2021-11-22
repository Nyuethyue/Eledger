package bhutan.eledger.adapter.out.persistence.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceBankInfo;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
class PaymentAdviceMapper {

    PaymentAdviceEntity mapToEntity(PaymentAdvice paymentAdvice) {

        var bankInfoEntity = new PaymentAdviceBankInfoEntity(
                paymentAdvice.getBankInfo().getId(),
                paymentAdvice.getBankInfo().getBankAccountNumber()
        );

        bankInfoEntity.setDescriptions(
                paymentAdvice.getBankInfo().getDescription()
                        .getTranslations()
                        .stream()
                        .map(t ->
                                new PaymentAdviceBankInfoDescriptionEntity(
                                        t.getId(),
                                        t.getLanguageCode(),
                                        t.getValue(),
                                        bankInfoEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        var paymentAdviceEntity = new PaymentAdviceEntity(
                paymentAdvice.getId(),
                paymentAdvice.getDrn(),
                paymentAdvice.getDueDate(),
                paymentAdvice.getPeriod().getYear(),
                paymentAdvice.getPeriod().getSegment(),
                paymentAdvice.getCreationDateTime(),
                paymentAdvice.getPan(),
                paymentAdvice.getStatus().getValue(),
                paymentAdvice.getTaxpayer(),
                bankInfoEntity
        );

        paymentAdviceEntity.setPayableLines(
                paymentAdvice.getPayableLines()
                        .stream()
                        .map(pl ->
                                new PayableLineEntity(
                                        pl.getId(),
                                        pl.getPaidAmount(),
                                        pl.getAmount(),
                                        paymentAdviceEntity,
                                        pl.getGlAccount()
                                )
                        )
                        .collect(Collectors.toSet())
        );


        return paymentAdviceEntity;
    }

    public PaymentAdvice mapToDomain(PaymentAdviceEntity paymentAdviceEntity) {
        PaymentAdvice.Period period = PaymentAdvice.Period.of(
                paymentAdviceEntity.getPeriodYear(),
                paymentAdviceEntity.getPeriodSegment()
        );

        PaymentAdviceBankInfoEntity bankInfoEntity = paymentAdviceEntity.getBankInfo();

        PaymentAdviceBankInfo bankInfo = PaymentAdviceBankInfo.withId(
                bankInfoEntity.getId(),
                bankInfoEntity.getBankAccountNumber(),
                Multilingual.of(bankInfoEntity.getDescriptions()
                )
        );

        Collection<PayableLine> payableLines = paymentAdviceEntity.getPayableLines()
                .stream()
                .map(pl ->
                        PayableLine.of(
                                pl.getId(),
                                pl.getGlAccount(),
                                pl.getPaidAmount(),
                                pl.getAmount()
                        )
                )
                .collect(Collectors.toUnmodifiableList());


        return PaymentAdvice.withId(
                paymentAdviceEntity.getId(),
                paymentAdviceEntity.getDrn(),
                paymentAdviceEntity.getDueDate(),
                period,
                paymentAdviceEntity.getCreationDateTime(),
                paymentAdviceEntity.getPan(),
                PaymentAdviceStatus.valueOf(paymentAdviceEntity.getStatus()),
                paymentAdviceEntity.getTaxpayer(),
                bankInfo,
                payableLines
        );
    }
}
