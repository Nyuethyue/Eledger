package bhutan.eledger.application.port.in.epayment.payment.rma;

public interface RollbackRmaPaymentUseCase {

    void rollback(RollbackRmaPaymentCommand command);

    record RollbackRmaPaymentCommand(Long paymentAdviceId) {
    }
}
