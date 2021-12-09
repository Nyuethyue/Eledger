package bhutan.eledger.application.port.out.ref.paymentmode;

import bhutan.eledger.domain.ref.paymentmode.PaymentMode;

import java.util.Collection;

public interface PaymentModeRepositoryPort {
    Long getIdByCode(String code);
    Collection<PaymentMode> readAll();
}
