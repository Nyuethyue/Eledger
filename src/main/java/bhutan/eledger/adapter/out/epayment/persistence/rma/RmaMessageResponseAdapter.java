package bhutan.eledger.adapter.out.epayment.persistence.rma;

import bhutan.eledger.application.port.out.epayment.rma.RmaMessageResponseRepositoryPort;
import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class RmaMessageResponseAdapter implements RmaMessageResponseRepositoryPort {
    private final RmaMessageResponseEntityRepository rmaMessageResponseEntityRepository;
    private final RmaMessageResponseMapper rmaMessageResponseMapper;

    @Override
    public Optional<RmaMessageResponse> findLastResponseByOrderNo(String orderNo) {
        return rmaMessageResponseEntityRepository.findByOrderNoOrderByCreationDateTimeDesc(orderNo)
                .map(rmaMessageResponseMapper::mapToDomain);
    }
}
