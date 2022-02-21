package bhutan.eledger.domain.epayment.rma;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RmaMessageResponse {
    private final Long id;
    private final RmaMsgType msgType;
    private final String bfsTxnId;
    private final LocalDateTime bfsTxnTime;
    private final LocalDateTime benfTxnTime;
    @EqualsAndHashCode.Include
    private final String orderNo;
    private final String benfId;
    private final String txnCurrency;
    private final BigDecimal txnAmount;
    private final String checkSum;
    private final String remitterName;
    private final String remitterBankId;
    @EqualsAndHashCode.Include
    private final DebitAuthCode debitAuthCode;
    private final String debitAuthNo;

    private final LocalDateTime creationDateTime;

    public static RmaMessageResponse withoutId(RmaMsgType msgType, String bfsTxnId, LocalDateTime bfsTxnTime, LocalDateTime benfTxnTime, String orderNo, String benfId, String txnCurrency, BigDecimal txnAmount, String checkSum, String remitterName, String remitterBankId, DebitAuthCode debitAuthCode, String debitAuthNo, LocalDateTime creationDateTime) {
        return new RmaMessageResponse(null, msgType, bfsTxnId, bfsTxnTime, benfTxnTime, orderNo, benfId, txnCurrency, txnAmount, checkSum, remitterName, remitterBankId, debitAuthCode, debitAuthNo, creationDateTime);
    }

    public static RmaMessageResponse withId(Long id, RmaMsgType msgType, String bfsTxnId, LocalDateTime bfsTxnTime, LocalDateTime benfTxnTime, String orderNo, String benfId, String txnCurrency, BigDecimal txnAmount, String checkSum, String remitterName, String remitterBankId, DebitAuthCode debitAuthCode, String debitAuthNo, LocalDateTime creationDateTime) {
        return new RmaMessageResponse(id, msgType, bfsTxnId, bfsTxnTime, benfTxnTime, orderNo, benfId, txnCurrency, txnAmount, checkSum, remitterName, remitterBankId, debitAuthCode, debitAuthNo, creationDateTime);
    }

}
