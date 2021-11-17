package bhutan.eledger.application.port.in.ref.paymentmode;

import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadPaymentModeUseCase {
    Collection<PaymentMode> readAll();
}
