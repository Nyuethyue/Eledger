package bhutan.eledger.common.dto;

import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "of")
class DateValidityPeriod implements ValidityPeriod<LocalDate> {
    private final LocalDate start;
    private final LocalDate end;

    @Override
    public ValidityPeriod<LocalDate> start(LocalDate start) {
        return of(start, end);
    }

    @Override
    public ValidityPeriod<LocalDate> end(LocalDate end) {
        return of(start, end);
    }
}
