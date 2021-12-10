package bhutan.eledger.common.ref.refentry;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RefName {
    CURRENCY("RefCurrency"),
    BANK_BRANCH("RefBankBranch");

    private final String value;

    public static RefName of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(element -> element.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
