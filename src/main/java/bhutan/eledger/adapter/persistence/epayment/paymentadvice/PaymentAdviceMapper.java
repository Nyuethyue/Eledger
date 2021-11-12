package bhutan.eledger.adapter.persistence.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.epayment.PaymentAdvice;
import bhutan.eledger.domain.epayment.PaymentAdviceBankInfo;
import bhutan.eledger.domain.epayment.PaymentAdviceStatus;
import bhutan.eledger.domain.epayment.PaymentLine;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
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
                paymentAdvice.getTpn(),
                paymentAdvice.getDueDate(),
                paymentAdvice.getPeriod().getStart(),
                paymentAdvice.getPeriod().getEnd(),
                paymentAdvice.getCreationDateTime(),
                paymentAdvice.getPan(),
                paymentAdvice.getStatus().getValue(),
                bankInfoEntity
        );

        paymentAdviceEntity.setPaymentLines(
                paymentAdvice.getPaymentLines()
                        .stream()
                        .map(pl -> {

                            var paGlAccEntity = new PaymentAdviceGLAccountEntity(
                                    pl.getGlAccount().getId(),
                                    pl.getGlAccount().getCode()
                            );

                            paGlAccEntity.setDescriptions(
                                    pl.getGlAccount().getDescription().getTranslations()
                                            .stream()
                                            .map(t ->
                                                    new PaymentAdviceGLAccountDescriptionEntity(
                                                            t.getId(),
                                                            t.getLanguageCode(),
                                                            t.getValue(),
                                                            paGlAccEntity
                                                    )
                                            )
                                            .collect(Collectors.toSet())
                            );

                            return new PaymentLineEntity(
                                    pl.getId(),
                                    pl.getPaidAmount(),
                                    pl.getAmount(),
                                    paymentAdviceEntity,
                                    paGlAccEntity
                            );

                        })
                        .collect(Collectors.toSet())
        );


        return paymentAdviceEntity;
    }

    public PaymentAdvice mapToDomain(PaymentAdviceEntity paymentAdviceEntity) {
        PaymentAdvice.Period period = PaymentAdvice.Period.of(
                paymentAdviceEntity.getPeriodStartDate(),
                paymentAdviceEntity.getPeriodEndDate());
        PaymentAdviceBankInfoEntity bankInfoEntity = paymentAdviceEntity.getBankInfo();
        PaymentAdviceBankInfo bankInfo = PaymentAdviceBankInfo.withId(
                bankInfoEntity.getId(),
                bankInfoEntity.getBankAccountNumber(),
                Multilingual.of(bankInfoEntity.getDescriptions()
                ));

        Collection<PaymentLine> paymentLines = new LinkedList<>();
        Set<PaymentLineEntity> plines = paymentAdviceEntity.getPaymentLines();

        if(null != plines) {
            for (PaymentLineEntity pl : plines) {
                paymentLines.add(
                        PaymentLine.of(
                                pl.getId(),
                                PaymentLine.GLAccount.withId(
                                        pl.getGlAccount().getId(),
                                        pl.getGlAccount().getCode(),
                                        Multilingual.of(pl.getGlAccount().getDescriptions())),
                                pl.getPaidAmount(),
                                pl.getAmount()));
            }
        }

        return PaymentAdvice.withId(
                paymentAdviceEntity.getId(),
                paymentAdviceEntity.getDrn(),
                paymentAdviceEntity.getTpn(),
                paymentAdviceEntity.getDueDate(),
                period,
                paymentAdviceEntity.getCreationDateTime(),
                paymentAdviceEntity.getPan(),
                PaymentAdviceStatus.valueOf(paymentAdviceEntity.getStatus()),
                bankInfo,
                paymentLines);
    }

}
