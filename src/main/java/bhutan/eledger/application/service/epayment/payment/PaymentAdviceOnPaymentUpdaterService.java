package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
class PaymentAdviceOnPaymentUpdaterService {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    PaymentAdvice updatePaymentAdvice(CreatePaymentCommonCommand command, PaymentAdvice paymentAdvice) {

        paymentAdvice.pay(
                PaymentAdvice.PaymentContext.of(
                        command.getPayableLines()
                                .stream()
                                .map(plc ->
                                        PaymentAdvice.PayableLinePaymentContext.of(
                                                plc.getPayableLineId(),
                                                plc.getPaidAmount()
                                        )
                                )
                                .collect(Collectors.toUnmodifiableSet())
                )
        );

        log.trace("Updating payment advice: {}", paymentAdvice);

        paymentAdviceRepositoryPort.update(paymentAdvice);

        return paymentAdvice;
    }
}
