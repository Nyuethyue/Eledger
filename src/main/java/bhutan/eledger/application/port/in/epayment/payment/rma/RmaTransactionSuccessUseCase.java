package bhutan.eledger.application.port.in.epayment.payment.rma;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface RmaTransactionSuccessUseCase {

    String processSuccess(@Valid RmaTransactionSuccessCommand command);

    @Data
    class RmaTransactionSuccessCommand {
        @NotNull
        private final String orderNo;
        private final String debitAuthCode;
        private final String txnAmount;

        public RmaTransactionSuccessCommand(String bfs_orderNo, String bfs_debitAuthCode, String bfs_txnAmount) {
            this.orderNo = bfs_orderNo;
            this.debitAuthCode = bfs_debitAuthCode;
            this.txnAmount = bfs_txnAmount;
        }
    }
}
