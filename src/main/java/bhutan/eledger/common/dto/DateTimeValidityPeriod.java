package bhutan.eledger.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "of")
class DateTimeValidityPeriod implements ValidityPeriod<LocalDateTime> {

    private final LocalDateTime start;
    private final LocalDateTime end;
}
