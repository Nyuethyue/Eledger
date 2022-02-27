package bhutan.eledger.domain.epayment.rma;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum DebitAuthCode {
    NONE(null, "AR sent but not action done yet."),
    USER_CANCELLED("UC", "User Cancelled."),
    APPROVED("00", "Approved"),
    INVALID_BENF("03", "Invalid Beneficiary"),
    BENF_ACCOUNT_CLOSED("05", "Beneficiary Account Closed"),
    INVALID_TRANSACTION("12", "Invalid Transaction"),
    INVALID_AMOUNT("13", "Invalid Amount"),
    INVALID_RESPONSE("20", "Invalid Response"),
    TRANSACTION_NOT_SUPPORTED("30", "Transaction Not Supported Or Format Error"),
    DUPLICATE_BENF_ORDER_NO("45", "Duplicate Beneficiary Order Number"),
    INVALID_CURRENCY("47", "Invalid Currency"),
    TRANSACTION_LIMIT_EXCEEDED("48", "Transaction Limit Exceeded"),
    INSUFFICIENT_FUNDS("51", "Insufficient Funds"),
    NO_SAVINGS_ACCOUNT("53", "No Savings Account"),
    TRANSACTION_NOT_PERMITTED("57", "Transaction Not Permitted"),
    WITHDRAWAL_LIMIT_EXCEEDED("61", "Withdrawal Limit Exceeded"),
    WITHDRAWAL_FREQUENCY_EXCEEDED("65", "Withdrawal Frequency Exceeded"),
    TRANSACTION_NOT_FOUND("76", "Transaction Not Found"),
    DECRYPTION_FAILED("78", "Decryption Failed"),
    BUYER_CANCEL_TRANSACTION("80", "Buyer Cancel Transaction"),
    INVALID_TRANSACTION_TYPE("84", "Invalid Transaction Type"),
    INTERNAL_ERROR_AT_BANK_SYSTEM("85", "Internal Error At Bank System"),
    TIME_OUT("TO", "Time out"),
    TRANSACTION_CANCELLED_BY_CUSTOMER("BC", "Transaction Cancelled By Customer"),
    INTERNAL_ERROR("FE", "Internal Error"),
    SESSION_TIMEOUT_AT_BFS_SECURE_ENTRY_PAGE("OA", "Session Timeout at BFS Secure Entry Page"),
    TRANSACTION_REJECTED_NOT_IN_OPERATING_HOURS("OE", "Transaction Rejected As Not In Operating Hours"),
    TRANSACTION_TIMEOUT("OF", "Transaction Timeout"),
    INVALID_BENF_BANK_CODE("SB", "Invalid Beneficiary Bank Code"),
    INVALID_MESSAGE("XE", "Invalid Message"),
    INVALID_TRANSACTION_TYPE_XT("XT", "Invalid Transaction Type"),
    INVALID_MESSAGE_IM("IM", "Invalid message"),
    NOT_FOUND("NF", "NOT FOUND");

    private final String value;
    private final String description;

    public static DebitAuthCode of(String value) {
        return Arrays.stream(values())
                .filter(element -> Objects.equals(element.value, value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
