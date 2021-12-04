package bhutan.eledger.domain.ref.bankbranch;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDate;

@Data(staticConstructor = "withId")
public class RefBankBranch {
    private final Long    id;
    private final String  code;
    private final String  branchCode;
    private final String  address;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Long    bankId;
    private final Multilingual description;

    public static RefBankBranch withoutId(
            String code,
            String branchCode,
            String address,
            ValidityPeriod<LocalDate> validityPeriod,
            Long   bankId,
            Multilingual description
    ) {

        return new RefBankBranch(
                null,
                code,
                branchCode,
                address,
                validityPeriod,
                bankId,
                description
        );
    }

}
