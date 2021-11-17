package bhutan.eledger.domain.ref.bank;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class RefBank {

    private final Long id;
    private final String bankName;
    private final String bfscCode;
    private final Multilingual description;

    public static RefBank withoutId(
            String bankName,
            String bfscCode,
            Multilingual description
    ) {
        return new RefBank(
                null,
                bankName,
                bfscCode,
                description
        );
    }
}
