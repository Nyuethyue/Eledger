package bhutan.eledger.domain.ref.bankbranch;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class RefBankBranch {
    private final Long    id;
    private final String  code;
    private final String  bfscCode;
    private final String  address;
    private final Long    bankId;
    private final Multilingual descriptions;

    public static RefBankBranch withoutId(
            String code,
            String bfscCode,
            String address,
            Long   bankId,
            Multilingual descriptions
    ) {

        return new RefBankBranch(
                null,
                code,
                bfscCode,
                address,
                bankId,
                descriptions
        );
    }

}
