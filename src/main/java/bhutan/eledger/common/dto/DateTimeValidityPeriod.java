package bhutan.eledger.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "of")
class DateTimeValidityPeriod implements ValidityPeriod<LocalDateTime> {

    private final LocalDateTime start;
    private final LocalDateTime end;

    @Override
    public ValidityPeriod<LocalDateTime> start(LocalDateTime start) {
        return ValidityPeriod.of(start, end);
    }

    @Override
    public ValidityPeriod<LocalDateTime> end(LocalDateTime end) {
        return ValidityPeriod.of(start, end);
    }
}
