package bhutan.eledger.application.service.epayment.paymentmode;

import bhutan.eledger.application.port.in.epayment.paymentmode.ReadPaymentModeUseCase;
import bhutan.eledger.application.port.out.epayment.paymentmode.PaymentModeRepositoryPort;
import bhutan.eledger.domain.epayment.PaymentMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadPaymentModeService implements ReadPaymentModeUseCase {
    private final PaymentModeRepositoryPort paymentModeRepositoryPort;

    @Override
    public Collection<PaymentMode> readAll() {
        log.trace("Reading all payment modes.");

        return paymentModeRepositoryPort.readAll();
    }
}
