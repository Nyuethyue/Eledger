package bhutan.eledger.application.port.in.epayment.payment.rma;

import lombok.Data;

public interface RmaTransactionSuccessUseCase {

    void processSuccess(RmaTransactionSuccessCommand command);

    @Data
    class RmaTransactionSuccessCommand {
        private final String orderNo;
        private final String debitAuthNo;
        private final String debitAuthCode;
        private final String txnAmount;

        public RmaTransactionSuccessCommand(String bfs_orderNo, String bfs_debitAuthNo, String bfs_debitAuthCode, String bfs_txnAmount) {
            this.orderNo = bfs_orderNo;
            this.debitAuthNo = bfs_debitAuthNo;
            this.debitAuthCode = bfs_debitAuthNo;
            this.txnAmount = bfs_debitAuthNo;
        }
    }
}
