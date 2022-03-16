package bhutan.eledger.application.service.epayment.payment.rma;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import am.iunetworks.lib.task.TaskManager;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionCancelUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionFailUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionSuccessUseCase;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.rma.RmaMessageResponseRepositoryPort;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class RmaTransactionService implements RmaTransactionSuccessUseCase, RmaTransactionCancelUseCase, RmaTransactionFailUseCase {

    private final TaskManager taskManager;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final RmaMessageResponseRepositoryPort rmaMessageResponseRepositoryPort;

    @Override
    public String processSuccess(RmaTransactionSuccessCommand command) {
        log.debug("Transaction with orderNo: {} has been succeed.", command.getOrderNo());

        awaitTasksCompletion(command.getOrderNo());

        return receiptRepositoryPort.getReceiptNumberByPaId(command.getOrderNo());
    }

    @Override
    public RmaMessageResponse processCancel(RmaTransactionCancelCommand command) {
        log.debug("Transaction with orderNo: {} has been cancelled.", command.getOrderNo());

        awaitTasksCompletion(command.getOrderNo());

        return getLastResponseMessageResponse(command.getOrderNo());
    }

    @Override
    public RmaMessageResponse processFail(RmaTransactionFailCommand command) {
        log.debug("Transaction with orderNo: {} has been failed.", command.getOrderNo());

        awaitTasksCompletion(command.getOrderNo());

        return getLastResponseMessageResponse(command.getOrderNo());
    }

    private void awaitTasksCompletion(String orderNo) {
        taskManager.awaitTasksCompletion(orderNo, RmaMessage.class.getSimpleName());
    }

    private RmaMessageResponse getLastResponseMessageResponse(String orderNo) {
        return rmaMessageResponseRepositoryPort.findLastResponseByOrderNo(orderNo)
                .orElseThrow(() ->
                        new RecordNotFoundException(
                                "RmaMessageResponse by orderNo: [" + orderNo + "] not found."
                        )
                );
    }
}


