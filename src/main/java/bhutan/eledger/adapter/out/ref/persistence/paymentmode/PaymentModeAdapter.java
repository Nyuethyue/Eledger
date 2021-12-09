package bhutan.eledger.adapter.out.ref.persistence.paymentmode;

import bhutan.eledger.application.port.out.ref.paymentmode.PaymentModeRepositoryPort;
import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PaymentModeAdapter implements PaymentModeRepositoryPort {
    private final PaymentModeEntityRepository paymentModeEntityRepository;
    private final PaymentModeMapper paymentModeMapper;

    @Override
    public Optional<Long> getIdByCode(String code) {
        Optional<PaymentModeEntity> res = paymentModeEntityRepository.findByCode(code);
        if(res.isEmpty()) {
            Optional.empty();
        }
        return Optional.of(res.get().getId());
    }

    @Override
    public Collection<PaymentMode> readAll() {
        return paymentModeEntityRepository.findAll()
                .stream()
                .map(paymentModeMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }


}

