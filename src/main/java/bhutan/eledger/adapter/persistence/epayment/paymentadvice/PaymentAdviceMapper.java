package bhutan.eledger.adapter.persistence.epayment.paymentadvice;

import bhutan.eledger.domain.epayment.PaymentAdvice;
import org.springframework.stereotype.Component;

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
}
