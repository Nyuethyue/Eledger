package bhutan.eledger.adapter.out.ref.persistence.paymentmode;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
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
    public Long getIdByCode(String code) {
        return paymentModeEntityRepository.findByCode(code).map(PaymentModeEntity::getId)
                .orElseThrow(() -> new RecordNotFoundException("Payment mode by code:" + code + " not found."));
    }

    @Override
    public Collection<PaymentMode> readAll() {
        return paymentModeEntityRepository.findAll()
                .stream()
                .map(paymentModeMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}

