package bhutan.eledger.application.service.epayment.payment.rma;

import am.iunetworks.lib.task.TaskManager;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionCancelUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionFailUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionSuccessUseCase;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class RmaTransactionSuccessService implements RmaTransactionSuccessUseCase, RmaTransactionCancelUseCase, RmaTransactionFailUseCase {

    private final TaskManager taskManager;

    @Override
    public void processSuccess(RmaTransactionSuccessCommand command) {
        log.debug("Transaction with orderNo: {} has been succeed.", command.getOrderNo());

        awaitTasksCompletion(command.getOrderNo());
    }

    @Override
    public void processCancel(RmaTransactionCancelCommand command) {
        log.debug("Transaction with orderNo: {} has been cancelled.", command.getOrderNo());

        awaitTasksCompletion(command.getOrderNo());
    }

    @Override
    public void processFail(RmaTransactionFailCommand command) {
        log.debug("Transaction with orderNo: {} has been failed.", command.getOrderNo());

        awaitTasksCompletion(command.getOrderNo());
    }

    private void awaitTasksCompletion(String orderNo) {
        taskManager.awaitTasksCompletion(orderNo, RmaMessage.class.getSimpleName());
    }
}


