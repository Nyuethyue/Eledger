package bhutan.eledger.domain.ref.bank;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefBank {

    private final Long id;
    private final String code;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Multilingual description;

    public static RefBank withoutId(
            String code,
            ValidityPeriod<LocalDate> validityPeriod,
            Multilingual description
    ) {
        return new RefBank(
                null,
                code,
                validityPeriod,
                description
        );
    }
}
