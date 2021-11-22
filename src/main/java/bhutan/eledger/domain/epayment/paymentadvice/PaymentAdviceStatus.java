package bhutan.eledger.domain.epayment.paymentadvice;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentAdviceStatus {
    INITIAL("INITIAL"),
    PAID("PAID"),
    PRE_RECONCILED("PRE_RECONCILED"),
    SPLIT_PAYMENT("SPLIT_PAYMENT");

    private final String value;

    public static PaymentAdviceStatus of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
