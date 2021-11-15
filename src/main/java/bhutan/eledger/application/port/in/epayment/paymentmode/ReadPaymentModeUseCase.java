package bhutan.eledger.application.port.in.epayment.paymentmode;

import bhutan.eledger.domain.epayment.PaymentMode;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadPaymentModeUseCase {
    Collection<PaymentMode> readAll();
}
