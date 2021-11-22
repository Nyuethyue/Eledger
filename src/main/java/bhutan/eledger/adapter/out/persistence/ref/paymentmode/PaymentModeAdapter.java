package bhutan.eledger.adapter.out.persistence.ref.paymentmode;

import bhutan.eledger.application.port.out.ref.paymentmode.PaymentModeRepositoryPort;
import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
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

