package bhutan.eledger.domain.ref.agencyglaccount;

import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefAgencyGLAccount {
    private final Long id;
    private final String code;
    private final String agencyCode;
    private final ValidityPeriod<LocalDate> validityPeriod;

    public static RefAgencyGLAccount withoutId(
            String code,
            String agencyCode,
            ValidityPeriod<LocalDate> validityPeriod
            ) {
        return new RefAgencyGLAccount(
                null,
                code,
                agencyCode,
                validityPeriod
        );
    }
}
