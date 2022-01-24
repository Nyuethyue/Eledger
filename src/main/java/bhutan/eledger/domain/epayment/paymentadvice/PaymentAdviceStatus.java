package bhutan.eledger.domain.epayment.paymentadvice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum PaymentAdviceStatus {
    INITIAL("INITIAL"),
    SPLIT_PAYMENT("SPLIT_PAYMENT"),
    PAID("PAID"),
    PRE_RECONCILED("PRE_RECONCILED"),
    RECONCILED("RECONCILED");

    private final String value;

    public static PaymentAdviceStatus of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                                "Illegal value: [" + value + "]. Possible values: " +
                                        Arrays.stream(values())
                                                .map(PaymentAdviceStatus::getValue)
                                                .collect(Collectors.toUnmodifiableList())
                        )
                );
    }
}
