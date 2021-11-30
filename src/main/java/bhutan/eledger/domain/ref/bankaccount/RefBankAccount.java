package bhutan.eledger.domain.ref.bankaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefBankAccount {
    private final Long   id;
    private final Long   branchId;
    private final String code;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Multilingual description;

    public static RefBankAccount withoutId(
            Long   branchId,
            String code,
            ValidityPeriod<LocalDate> validityPeriod,
            Multilingual description
    ) {
        return new RefBankAccount(
                null,
                branchId,
                code,
                validityPeriod,
                description
        );
    }
}
