package bhutan.eledger.application.port.in.epayment.payment.rma;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

public interface RmaTransactionSuccessUseCase {

    void processSuccess(RmaTransactionSuccessCommand command);

    @Data
    class RmaTransactionSuccessCommand {
        private final String orderNo;

        @JsonCreator
        public RmaTransactionSuccessCommand(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
