package bhutan.eledger.domain.eledger.config.glaccount;

import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "of")
public class ValidityPeriod {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public static ValidityPeriod withOnlyStartOfValidity(LocalDateTime start) {
        return of(start, null);
    }
}
