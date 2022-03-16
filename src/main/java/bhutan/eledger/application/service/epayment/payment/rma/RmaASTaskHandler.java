package bhutan.eledger.application.service.epayment.payment.rma;

import am.iunetworks.lib.task.Task;
import bhutan.eledger.application.port.in.epayment.payment.rma.CompleteRmaPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RollbackRmaPaymentUseCase;
import bhutan.eledger.application.port.out.epayment.rma.RmaMessageRepositoryPort;
import bhutan.eledger.application.port.out.epayment.rma.RmaRequesterPort;
import bhutan.eledger.common.task.ExtendedTaskHandler;
import bhutan.eledger.configuration.epayment.rma.RmaProperties;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import bhutan.eledger.domain.epayment.rma.RmaMessageStatus;
import bhutan.eledger.domain.epayment.task.EpTaskType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.Set;

@Log4j2
@Component
@RequiredArgsConstructor
class RmaASTaskHandler implements ExtendedTaskHandler {
    private static final Collection<RmaMessageStatus> COMPLETE_TASK_STATUSES = Set.of(
            RmaMessageStatus.COMPLETED,
            RmaMessageStatus.FAILED,
            RmaMessageStatus.CANCELLED
    );

    private final RmaRequesterPort rmaRequesterPort;
    private final RmaMessageRepositoryPort rmaMessageRepositoryPort;
    private final CompleteRmaPaymentUseCase completeRmaPaymentUseCase;
    private final RollbackRmaPaymentUseCase rollbackRmaPaymentUseCase;
    private final RmaProperties rmaProperties;
    private final TransactionTemplate transactionTemplate;

    @Override
    public String getTaskType() {
        return EpTaskType.RMA_AS.getValue();
    }

    @Override
    public boolean handle(Task task) {

        return Boolean.TRUE.equals(transactionTemplate.execute(status -> {

                    log.trace("Handling task: {}", task);

                    RmaMessage rmaMessage =
                            rmaMessageRepositoryPort.requiredReadByOrderNo(task.getOriginatorId());

                    var rmaMessageResponse = rmaRequesterPort.send(rmaMessage.getAsPart());

                    rmaMessage.addToRmaMessageResponses(rmaMessageResponse);

                    processMessageStatus(task, rmaMessage, rmaMessageResponse);

                    rmaMessageRepositoryPort.update(rmaMessage);

                    processPayment(rmaMessage, rmaMessageResponse);

                    return COMPLETE_TASK_STATUSES.contains(rmaMessage.getStatus());
                })
        );
    }

    private void processPayment(RmaMessage rmaMessage, RmaMessageResponse rmaMessageResponse) {
        if (RmaMessageStatus.COMPLETED == rmaMessage.getStatus()) {

            log.debug("Rma transaction completed. OrderNo: {}, PA_ID: {}, amount: {}", rmaMessage.getOrderNo(), rmaMessage.getPaymentAdviceId(), rmaMessageResponse.getTxnAmount());

            completeRmaPaymentUseCase.complete(
                    new CompleteRmaPaymentUseCase.CompleteRmaPaymentCommand(
                            rmaMessage.getPaymentAdviceId(),
                            rmaMessageResponse.getTxnAmount()
                    )
            );

        } else if (RmaMessageStatus.FAILED == rmaMessage.getStatus() || RmaMessageStatus.CANCELLED == rmaMessage.getStatus()) {
            rollbackRmaPaymentUseCase.rollback(
                    new RollbackRmaPaymentUseCase.RollbackRmaPaymentCommand(
                            rmaMessage.getPaymentAdviceId()
                    )
            );
        }
    }

    private void processMessageStatus(Task task, RmaMessage rmaMessage, RmaMessageResponse rmaMessageResponse) {
        switch (rmaMessageResponse.getDebitAuthCode()) {
            case APPROVED -> rmaMessage.complete();

            case NOT_FOUND, INVALID_RESPONSE -> {
                if (task.getTriggerCount() >= rmaProperties.getTask().getRetryCountUntilFail() - 1) {
                    rmaMessage.fail();
                }
            }

            case NONE -> log.trace("Rma AR with order id: {} sent but no action is done yet.", rmaMessage.getOrderNo());

            case INTERNAL_ERROR -> log.warn("Internal error from rma side. Response: {}", rmaMessageResponse);

            case USER_CANCELLED -> rmaMessage.cancel();

            default -> rmaMessage.fail();
        }
    }
}
