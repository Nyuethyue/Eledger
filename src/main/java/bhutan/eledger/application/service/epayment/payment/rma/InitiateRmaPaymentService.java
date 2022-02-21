package bhutan.eledger.application.service.epayment.payment.rma;

import am.iunetworks.lib.task.Task;
import am.iunetworks.lib.task.TaskService;
import bhutan.eledger.application.port.in.epayment.payment.rma.InitiateRmaPaymentUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.epayment.rma.RmaMessageRepositoryPort;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import bhutan.eledger.domain.epayment.rma.RmaMsgType;
import bhutan.eledger.domain.epayment.task.EpTaskType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class InitiateRmaPaymentService implements InitiateRmaPaymentUseCase {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final TaskService taskService;
    private final RmaMessageRepositoryPort rmaMessageRepositoryPort;
    private final RmaMessagePartCreator rmaMessagePartCreator;

    @Override
    public void initiate(InitiateRmaPaymentCommand command) {
        log.trace("Creating rma payment with command: {}", command);

        var rmaMessage = rmaMessageRepositoryPort.requiredReadByOrderNo(command.getOrderNo());

        var paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(rmaMessage.getPaymentAdviceId());

        paymentAdvice.initiate();

        paymentAdviceRepositoryPort.updateStatus(paymentAdvice);

        var rmaMessageAsPart = rmaMessagePartCreator.create(
                RmaMessagePartCreator.RmaRequestDataCreatorContext.of(
                        RmaMsgType.AS,
                        paymentAdvice,
                        rmaMessage.getArPart().getTxnCurrency(),
                        rmaMessage.getOrderNo(),
                        rmaMessage.getArPart().getBenfTxnTime()
                )
        );

        rmaMessage.setAsPart(rmaMessageAsPart);

        rmaMessage.pending();

        rmaMessageRepositoryPort.update(rmaMessage);

        log.debug("Payment advice with pan: [{}] has been initiated.", paymentAdvice.getPan());

        var persistedTask = taskService.save(
                Task.builder(
                                EpTaskType.RMA_AS.getValue()
                        )
                        .originatorId(rmaMessage.getOrderNo())
                        .originatorName(RmaMessage.class.getSimpleName())
                        .build()
        );

        log.debug("Rma message's task with id: {} successfully persisted.", persistedTask.getId());

    }
}
