package bhutan.eledger.adapter.persistence.epayment.paymentmode;

import bhutan.eledger.application.port.out.epayment.paymentmode.PaymentModeRepositoryPort;
import bhutan.eledger.domain.epayment.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PaymentModeAdapter implements PaymentModeRepositoryPort {
    private final PaymentModeEntityRepository paymentModeEntityRepository;
    private final PaymentModeMapper paymentModeMapper;

    @Override
    public Collection<PaymentMode> readAll() {
        return paymentModeEntityRepository.findAll()
                .stream()
                .map(paymentModeMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}

