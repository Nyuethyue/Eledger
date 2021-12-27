package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.domain.epayment.payment.Payment;
import bhutan.eledger.domain.epayment.payment.PaymentPaInfo;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
class PaymentsResolverService {

    Collection<Payment> resolvePayments(CreatePaymentCommonCommand command, PaymentAdvice paymentAdvice) {
        return command.getPayments()
                .stream()
                .map(pc -> {

                    PayableLine payableLine = paymentAdvice.getRequiredPayableLineById(pc.getPayableLineId());

                    return Payment.withoutId(
                            payableLine.getGlAccount(),
                            pc.getPaidAmount(),
                            payableLine.getId(),
                            payableLine.getElTransactionId(),
                            PaymentPaInfo.withoutId(
                                    paymentAdvice.getId(),
                                    paymentAdvice.getPan(),
                                    paymentAdvice.getDrn()
                            )
                    );
                })
                .collect(Collectors.toUnmodifiableList());
    }

}
