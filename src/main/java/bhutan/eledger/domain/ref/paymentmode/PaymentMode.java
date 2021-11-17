package bhutan.eledger.domain.ref.paymentmode;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class PaymentMode {
    private final Long id;
    private final String code;
    private final Multilingual description;
}
