package bhutan.eledger.domain.epayment.rma;

import com.google.common.collect.ImmutableSortedSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RmaMessagePart {

    private static final SortedSet<ArAsRequestDataElement> HASHABLE_ELEMENTS = ImmutableSortedSet.of(
                    ArAsRequestDataElement.MSG_TYPE,
                    ArAsRequestDataElement.BENF_TXN_TIME,
                    ArAsRequestDataElement.ORDER_NO,
                    ArAsRequestDataElement.BENF_ID,
                    ArAsRequestDataElement.BENF_BANK_CODE,
                    ArAsRequestDataElement.TXN_CURRENCY,
                    ArAsRequestDataElement.TXN_AMOUNT,
                    ArAsRequestDataElement.REMITTER_EMAIL,
                    ArAsRequestDataElement.PAYMENT_DESC,
                    ArAsRequestDataElement.VERSION
            )
            .stream()
            .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.comparing(ArAsRequestDataElement::getValue)));

    private final Long id;
    private final RmaMsgType msgType;
    private final LocalDateTime benfTxnTime;
    private final String orderNo;
    private final String benfId;
    private final String benfBankCode;
    private final String txnCurrency;
    private final BigDecimal txnAmount;
    private final String remitterEmail;
    private String checkSum;
    private final String paymentDesc;
    private final Double version;
    private final LocalDateTime creationDateTime;

    private Map<String, String> bfsNameValuePair;
    private String messageHash;

    public RmaMessagePart(Long id, RmaMsgType msgType, LocalDateTime benfTxnTime, String orderNo, String benfId, String benfBankCode, String txnCurrency, BigDecimal txnAmount, String remitterEmail, String checkSum, String paymentDesc, Double version, LocalDateTime creationDateTime) {
        this.id = id;
        this.msgType = msgType;
        this.benfTxnTime = benfTxnTime;
        this.orderNo = orderNo;
        this.benfId = benfId;
        this.benfBankCode = benfBankCode;
        this.txnCurrency = txnCurrency;
        this.txnAmount = txnAmount;
        this.remitterEmail = remitterEmail;
        this.checkSum = checkSum;
        this.paymentDesc = paymentDesc;
        this.version = version;
        this.creationDateTime = creationDateTime;
    }

    public static RmaMessagePart createWithoutId(
            RmaMsgType rmaMsgType,
            LocalDateTime benfTxnTime,
            String orderNo,
            String benfId,
            String benfBankCode,
            String txnCurrency,
            BigDecimal txnAmount,
            String remitterEmail,
            String paymentDesc,
            Double version,
            LocalDateTime creationDateTime
    ) {
        return new RmaMessagePart(
                null,
                rmaMsgType,
                benfTxnTime,
                orderNo,
                benfId,
                benfBankCode,
                txnCurrency,
                txnAmount,
                remitterEmail,
                paymentDesc,
                version,
                creationDateTime
        );
    }

    public static RmaMessagePart withId(
            Long id,
            RmaMsgType rmaMsgType,
            LocalDateTime benfTxnTime,
            String orderNo,
            String benfId,
            String benfBankCode,
            String txnCurrency,
            BigDecimal txnAmount,
            String remitterEmail,
            String checkSum,
            String paymentDesc,
            Double version,
            LocalDateTime creationDateTime
    ) {
        return new RmaMessagePart(
                id,
                rmaMsgType,
                benfTxnTime,
                orderNo,
                benfId,
                benfBankCode,
                txnCurrency,
                txnAmount,
                remitterEmail,
                checkSum,
                paymentDesc,
                version,
                creationDateTime
        );
    }

    public String getMessageHash() {
        if (messageHash == null) {
            calculateMessageHash();
        }

        return messageHash;
    }

    public Map<String, String> getBfsNameValuePair() {
        initiateBfsNameValuePair();
        return Map.copyOf(bfsNameValuePair);
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
        bfsNameValuePair.put(ArAsRequestDataElement.CHECK_SUM.getValue(), checkSum);
    }

    private void calculateMessageHash() {

        initiateBfsNameValuePair();

        messageHash = HASHABLE_ELEMENTS.stream()
                .map(ArAsRequestDataElement::getValue)
                .map(bfsNameValuePair::get)
                .collect(Collectors.joining("|"));
    }

    private void initiateBfsNameValuePair() {
        if (CollectionUtils.isEmpty(bfsNameValuePair)) {
            bfsNameValuePair = new HashMap<>();

            bfsNameValuePair.put(ArAsRequestDataElement.MSG_TYPE.getValue(), msgType.getValue());
            bfsNameValuePair.put(ArAsRequestDataElement.BENF_TXN_TIME.getValue(), RmaMessage.txnDateTimeToString(benfTxnTime));
            bfsNameValuePair.put(ArAsRequestDataElement.ORDER_NO.getValue(), orderNo);
            bfsNameValuePair.put(ArAsRequestDataElement.BENF_ID.getValue(), benfId);
            bfsNameValuePair.put(ArAsRequestDataElement.BENF_BANK_CODE.getValue(), benfBankCode);
            bfsNameValuePair.put(ArAsRequestDataElement.TXN_CURRENCY.getValue(), txnCurrency);
            bfsNameValuePair.put(ArAsRequestDataElement.TXN_AMOUNT.getValue(), txnAmount.toString());
            bfsNameValuePair.put(ArAsRequestDataElement.REMITTER_EMAIL.getValue(), remitterEmail);
            bfsNameValuePair.put(ArAsRequestDataElement.CHECK_SUM.getValue(), checkSum);
            bfsNameValuePair.put(ArAsRequestDataElement.PAYMENT_DESC.getValue(), paymentDesc);
            bfsNameValuePair.put(ArAsRequestDataElement.VERSION.getValue(), version.toString());
        }
    }
}


