package bhutan.eledger.application.port.in.epayment.payment.rma;

import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface RmaTransactionFailUseCase {

    RmaMessageResponse processFail(@Valid RmaTransactionFailCommand command);

    @Data
    class RmaTransactionFailCommand {
        @NotNull
        private final String orderNo;

        public RmaTransactionFailCommand(String bfs_orderNo) {
            this.orderNo = bfs_orderNo;
        }
    }
}
