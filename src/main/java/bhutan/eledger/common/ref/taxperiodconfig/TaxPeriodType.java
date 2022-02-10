package bhutan.eledger.common.ref.taxperiodconfig;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TaxPeriodType {
    FORTNIGHTLY("FORTNIGHTLY"),
    MONTHLY("MONTHLY"),
    QUARTERLY("QUARTERLY"),
    HALFYEARLY("HALF_YEARLY"),
    YEARLY("YEARLY");

    private final String value;

    public static TaxPeriodType of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(element -> element.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
