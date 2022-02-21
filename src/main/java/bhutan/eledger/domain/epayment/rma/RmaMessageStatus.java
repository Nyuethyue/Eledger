package bhutan.eledger.domain.epayment.rma;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RmaMessageStatus {
    CREATED("CREATED"),
    PENDING("PENDING"),
    FAILED("FAILED"),
    COMPLETED("COMPLETED");

    private final String value;

    public static RmaMessageStatus of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
