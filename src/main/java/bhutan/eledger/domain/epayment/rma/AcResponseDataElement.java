package bhutan.eledger.domain.epayment.rma;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcResponseDataElement {
    MSG_TYPE("bfs_msgType"),
    BFS_TXN_ID("bfs_bfsTxnId"),
    BFS_TXN_TIME("bfs_bfsTxnTime"),
    BENF_TXN_TIME("bfs_benfTxnTime"),
    ORDER_NO("bfs_orderNo"),
    BENF_ID("bfs_benfId"),
    TXN_CURRENCY("bfs_txnCurrency"),
    TXN_AMOUNT("bfs_txnAmount"),
    CHECK_SUM("bfs_checkSum"),
    REMITTER_NAME("bfs_remitterName"),
    REMITTER_BANK_ID("bfs_remitterBankId"),
    DEBIT_AUTH_CODE("bfs_debitAuthCode"),
    DEBIT_AUTH_NO("bfs_debitAuthNo");

    private final String value;

}
