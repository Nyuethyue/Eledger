package bhutan.eledger.application.port.out.ref.paymentmode;

import bhutan.eledger.domain.ref.paymentmode.PaymentMode;

import java.util.Collection;

public interface PaymentModeRepositoryPort {
    Collection<PaymentMode> readAll();
}
