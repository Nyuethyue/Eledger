package bhutan.eledger.domain.config.glaccount;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GLAccountStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    public static GLAccountStatus of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
