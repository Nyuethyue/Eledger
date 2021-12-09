package bhutan.eledger.application.port.out.ref.paymentmode;

import bhutan.eledger.domain.ref.paymentmode.PaymentMode;

import java.util.Collection;
import java.util.Optional;

public interface PaymentModeRepositoryPort {
    Optional<Long> getIdByCode(String code);
    Collection<PaymentMode> readAll();
}
