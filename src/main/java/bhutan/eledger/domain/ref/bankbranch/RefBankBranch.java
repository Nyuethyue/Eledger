package bhutan.eledger.domain.ref.bankbranch;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class RefBankBranch {
    private final Long id;
    private final String branchCode;
    private final String branchName;
    private final String branchBfscCode;
    private final String location;
    private final Long    bankId;
    private final Multilingual description;

    public static RefBankBranch withoutId(
            String branchCode,
            String branchName,
            String branchBfscCode,
            String location,
            Long    bankId,
            Multilingual description
    ) {

        return new RefBankBranch(
                null,
                branchCode,
                branchName,
                branchBfscCode,
                location,
                bankId,
                description
        );
    }

}
