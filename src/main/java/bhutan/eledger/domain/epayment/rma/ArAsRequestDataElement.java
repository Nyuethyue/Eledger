package bhutan.eledger.domain.epayment.rma;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArAsRequestDataElement {
    MSG_TYPE("bfs_msgType"),
    BENF_TXN_TIME("bfs_benfTxnTime"),
    ORDER_NO("bfs_orderNo"),
    BENF_ID("bfs_benfId"),
    BENF_BANK_CODE("bfs_benfBankCode"),
    TXN_CURRENCY("bfs_txnCurrency"),
    TXN_AMOUNT("bfs_txnAmount"),
    REMITTER_EMAIL("bfs_remitterEmail"),
    CHECK_SUM("bfs_checkSum"),
    PAYMENT_DESC("bfs_paymentDesc"),
    VERSION("bfs_version");

    private final String value;

}
