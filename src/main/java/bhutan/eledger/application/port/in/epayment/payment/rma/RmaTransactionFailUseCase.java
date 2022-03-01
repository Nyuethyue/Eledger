package bhutan.eledger.application.port.in.epayment.payment.rma;

import lombok.Data;

public interface RmaTransactionFailUseCase {

    void processFail(RmaTransactionFailCommand command);

    @Data
    class RmaTransactionFailCommand {
        private final String orderNo;

        public RmaTransactionFailCommand(String bfs_orderNo) {
            this.orderNo = bfs_orderNo;
        }
    }
}
