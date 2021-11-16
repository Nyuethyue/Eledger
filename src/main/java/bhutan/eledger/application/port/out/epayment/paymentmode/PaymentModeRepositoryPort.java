package bhutan.eledger.application.port.out.epayment.paymentmode;

import bhutan.eledger.domain.epayment.PaymentMode;

import java.util.Collection;

public interface PaymentModeRepositoryPort {
    Collection<PaymentMode> readAll();
}
