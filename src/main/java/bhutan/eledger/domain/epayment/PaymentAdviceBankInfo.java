package bhutan.eledger.domain.epayment;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class PaymentAdviceBankInfo {
    private final Long id;
    private final String bankAccountNumber;
    private final Multilingual description;

    public static PaymentAdviceBankInfo withoutId(String bankAccountNumber, Multilingual bankName) {
        return new PaymentAdviceBankInfo(
                null,
                bankAccountNumber,
                bankName
        );
    }
}
