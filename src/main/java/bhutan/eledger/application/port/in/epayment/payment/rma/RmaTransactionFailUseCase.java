package bhutan.eledger.application.port.in.epayment.payment.rma;

import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import lombok.Data;

public interface RmaTransactionFailUseCase {

    RmaMessageResponse processFail(RmaTransactionFailCommand command);

    @Data
    class RmaTransactionFailCommand {
        private final String orderNo;

        public RmaTransactionFailCommand(String bfs_orderNo) {
            this.orderNo = bfs_orderNo;
        }
    }
}
