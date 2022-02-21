package bhutan.eledger.domain.epayment.rma;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RmaMessage {
    private static final DateTimeFormatter TRANSACTION_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final Long id;
    private final String orderNo;

    private RmaMessageStatus status;
    private final Long paymentAdviceId;
    private final LocalDateTime creationDateTime;

    private final RmaMessagePart arPart;
    private RmaMessagePart asPart;
    private Set<RmaMessageResponse> rmaMessageResponses;

    private RmaMessage(Long id, RmaMessageStatus status, Long paymentAdviceId, String orderNo, LocalDateTime creationDateTime, RmaMessagePart arPart) {
        this.id = id;
        this.status = status;
        this.paymentAdviceId = paymentAdviceId;
        this.creationDateTime = creationDateTime;
        this.orderNo = orderNo;
        this.arPart = arPart;
    }

    public void pending() {
        status = RmaMessageStatus.PENDING;
    }

    public void complete() {
        status = RmaMessageStatus.COMPLETED;
    }

    public void fail() {
        status = RmaMessageStatus.FAILED;
    }

    public Collection<RmaMessageResponse> getRmaMessageResponses() {
        return CollectionUtils.isEmpty(rmaMessageResponses) ? Set.of() : rmaMessageResponses;
    }

    public void setAsPart(RmaMessagePart asPart) {
        this.asPart = asPart;
    }

    public void addToRmaMessageResponses(RmaMessageResponse rmaMessageResponse) {
        if (rmaMessageResponses == null) {
            rmaMessageResponses = new HashSet<>();
        }

        rmaMessageResponses.add(rmaMessageResponse);
    }

    public static RmaMessage createWithAr(
            Long paymentAdviceId,
            String orderNo,
            LocalDateTime creationDateTime,
            RmaMessagePart rmaMessagePart
    ) {
        return new RmaMessage(
                null,
                RmaMessageStatus.CREATED,
                paymentAdviceId,
                orderNo,
                creationDateTime,
                rmaMessagePart
        );
    }

    public static RmaMessage withId(Long id, String orderNo, RmaMessageStatus status, Long paymentAdviceId, LocalDateTime creationDateTime, RmaMessagePart arPart, RmaMessagePart asPart, Set<RmaMessageResponse> rmaMessageResponses) {
        return new RmaMessage(id, orderNo, status, paymentAdviceId, creationDateTime, arPart, asPart, rmaMessageResponses);
    }

    public static String txnDateTimeToString(LocalDateTime txnDateTime) {
        return TRANSACTION_DATE_TIME_FORMATTER.format(txnDateTime);
    }

    public static LocalDateTime txnDateTimeStringToDateTime(String txnDateTimeString) {
        return LocalDateTime.parse(txnDateTimeString, TRANSACTION_DATE_TIME_FORMATTER);
    }
}
