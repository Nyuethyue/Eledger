package bhutan.eledger.domain.epayment.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentMode {
    CASH("CASH"),
    CHEQUE("CHEQUE"),
    DEMAND_DRAFT("DEMAND_DRAFT"),
    CASH_WARRANT("CASH_WARRANT"),
    ADVANCE_DEPOSIT("ADVANCE_DEPOSIT"),
    POS("POS");

    private final String value;

    public static PaymentMode of(@NonNull String value) {
        return Arrays.stream(values())
                .filter(element -> element.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal value: [" + value + "]. Possible values: " + Arrays.toString(values())));
    }
}
