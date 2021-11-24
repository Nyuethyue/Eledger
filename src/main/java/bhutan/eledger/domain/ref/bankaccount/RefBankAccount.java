package bhutan.eledger.domain.ref.bankaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class RefBankAccount {
    private final Long   id;
    private final Long   branchId;
    private final String accNumber;
    private final Multilingual description;

    public static RefBankAccount withoutId(
            Long   branchId,
            String accNumber,
            Multilingual description
    ) {
        return new RefBankAccount(
                null,
                branchId,
                accNumber,
                description
        );
    }
}
