package bhutan.eledger.application.port.in.epayment.payment.rma;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

public interface RmaTransactionCancelUseCase {

    void processCancel(RmaTransactionCancelCommand command);

    @Data
    class RmaTransactionCancelCommand {
        private final String orderNo;

        @JsonCreator
        public RmaTransactionCancelCommand(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
