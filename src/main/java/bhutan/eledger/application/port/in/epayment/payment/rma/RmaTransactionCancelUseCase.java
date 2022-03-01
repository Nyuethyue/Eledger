package bhutan.eledger.application.port.in.epayment.payment.rma;

import lombok.Data;

public interface RmaTransactionCancelUseCase {

    void processCancel(RmaTransactionCancelCommand command);

    @Data
    class RmaTransactionCancelCommand {

        private final String orderNo;

        /**
         * @param bfs_orderNo request parameter "bfs_orderNo" is mapped by constructor before pass to controller
         */
        public RmaTransactionCancelCommand(String bfs_orderNo) {
            this.orderNo = bfs_orderNo;
        }
    }
}
