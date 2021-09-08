package bhutan.eledger.domain.config.balanceaccount;

import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "of")
public class ValidityPeriod {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public static ValidityPeriod withOnlyOfValidity(LocalDateTime start) {
        return of(start, null);
    }
}
