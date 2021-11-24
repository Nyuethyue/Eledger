package bhutan.eledger.domain.ref.bankbranch;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class RefBankBranch {
    private final Long    id;
    private final String  code;
    private final String  branchCode;
    private final String  address;
    private final Long    bankId;
    private final Multilingual description;

    public static RefBankBranch withoutId(
            String code,
            String branchCode,
            String address,
            Long   bankId,
            Multilingual description
    ) {

        return new RefBankBranch(
                null,
                code,
                branchCode,
                address,
                bankId,
                description
        );
    }

}
