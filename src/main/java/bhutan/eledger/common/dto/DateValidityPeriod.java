package bhutan.eledger.common.dto;

import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "of")
class DateValidityPeriod implements ValidityPeriod<LocalDate> {
    private final LocalDate start;
    private final LocalDate end;

}
