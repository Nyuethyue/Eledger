package bhutan.eledger.adapter.persistence.ref.paymentmode;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
import org.springframework.stereotype.Component;

@Component
class PaymentModeMapper {

    PaymentMode mapToDomain(PaymentModeEntity paymentModeEntity) {
        return PaymentMode.withId(
                paymentModeEntity.getId(),
                paymentModeEntity.getCode(),
                Multilingual.of(paymentModeEntity.getDescriptions())
        );
    }
}

