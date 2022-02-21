package bhutan.eledger.application.service.epayment.payment.rma;

import bhutan.eledger.application.port.in.epayment.payment.rma.CompleteRmaPaymentUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CompleteRmaPaymentService implements CompleteRmaPaymentUseCase {

    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @Override
    public void complete(CompleteRmaPaymentCommand command) {
        PaymentAdvice paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.getPaymentAdviceId());

        paymentAdvice.pay(command.getPaidAmount());

        paymentAdviceRepositoryPort.update(paymentAdvice);
    }
}
