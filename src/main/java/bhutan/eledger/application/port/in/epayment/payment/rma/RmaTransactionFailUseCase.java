package bhutan.eledger.application.port.in.epayment.payment.rma;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

public interface RmaTransactionFailUseCase {

    void processFail(RmaTransactionFailCommand command);

    @Data
    class RmaTransactionFailCommand {
        private final String orderNo;

        @JsonCreator
        public RmaTransactionFailCommand(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
