package bhutan.eledger.common.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public interface ValidityPeriod<T extends Temporal> {

    T getStart();

    T getEnd();

    ValidityPeriod<T> start(T start);

    ValidityPeriod<T> end(T end);

    static ValidityPeriod<LocalDateTime> of(LocalDateTime start, LocalDateTime end) {
        return DateTimeValidityPeriod.of(start, end);
    }

    static ValidityPeriod<LocalDateTime> withOnlyStartOfValidity(LocalDateTime start) {
        return of(start, null);
    }

    static ValidityPeriod<LocalDate> of(LocalDate start, LocalDate end) {
        return DateValidityPeriod.of(start, end);
    }

    static ValidityPeriod<LocalDate> withOnlyStartOfValidity(LocalDate start) {
        return of(start, null);
    }
}
