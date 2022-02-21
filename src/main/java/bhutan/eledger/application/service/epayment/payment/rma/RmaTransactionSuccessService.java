package bhutan.eledger.application.service.epayment.payment.rma;

import am.iunetworks.lib.task.TaskManager;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionSuccessUseCase;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class RmaTransactionSuccessService implements RmaTransactionSuccessUseCase {

    private final TaskManager taskManager;

    @Override
    public void processSuccess(RmaTransactionSuccessCommand command) {
        taskManager.awaitTasksCompletion(command.getOrderNo(), RmaMessage.class.getSimpleName());
    }
}


