package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpdatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.glaccount.EpGLAccountRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePaymentAdviceService implements UpdatePaymentAdviceUseCase {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final EpGLAccountRepositoryPort glAccountRepositoryPort;

    @Override
    public void update(Long id, UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command) {
        update(
                paymentAdviceRepositoryPort.readById(id).orElseThrow(() -> new RecordNotFoundException("Payment advice by id: [" + id + "] not found.")),
                command
        );
    }

    @Override
    public void update(PaymentAdvice paymentAdvice, UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command) {
        log.trace("Updating payment advice {}, by command: {}", paymentAdvice, command);

        if (paymentAdvice.getStatus() != PaymentAdviceStatus.INITIAL) {//todo clarification needed
            log.warn("Update not implemented for status: " + paymentAdvice.getStatus());
            return;
        }

        command.getPayableLines()
                .stream()
                .map(plc ->
                        PayableLine.withoutId(
                                glAccountRepositoryPort.requiredReadByCode(plc.getGlAccount().getCode()),
                                plc.getAmount(),
                                plc.getTransactionId()
                        )
                )
                .forEach(paymentAdvice::upsertPaymentLine);

        log.trace("Updating payment advice: {}", paymentAdvice);

        paymentAdviceRepositoryPort.update(paymentAdvice);

        log.debug("Payment advice by id: {} successfully updated.", paymentAdvice.getId());
    }
}
