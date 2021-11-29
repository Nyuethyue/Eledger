package bhutan.eledger.domain.epayment.payment;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ReceiptStatus {
    PAID("PAID"),
    PENDING_RECONCILIATION("PENDING_RECONCILIATION"),
    PRE_RECONCILIATION("PRE_RECONCILIATION"),
    RECONCILED("RECONCILED"),
    CANCELLED("CANCELLED"),
    BOUNCED("BOUNCED"),
    SPLIT_PAYMENT("SPLIT_PAYMENT");

    private final String value;

    public static ReceiptStatus of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(element -> element.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
