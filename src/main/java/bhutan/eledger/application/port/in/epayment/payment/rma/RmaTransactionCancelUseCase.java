package bhutan.eledger.application.port.in.epayment.payment.rma;

import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface RmaTransactionCancelUseCase {

    RmaMessageResponse processCancel(@Valid RmaTransactionCancelCommand command);

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
