package bhutan.eledger.application.service.epayment.payment.rma;

import bhutan.eledger.application.port.in.epayment.payment.rma.RollbackRmaPaymentUseCase;
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
class RollbackRmaPaymentService implements RollbackRmaPaymentUseCase {

    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @Override
    public void rollback(RollbackRmaPaymentCommand command) {
        PaymentAdvice paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.paymentAdviceId());

        paymentAdvice.rollbackFromInitiated();

        paymentAdviceRepositoryPort.updateStatus(paymentAdvice);
    }
}
