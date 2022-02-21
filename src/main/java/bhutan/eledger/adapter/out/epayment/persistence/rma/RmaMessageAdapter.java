package bhutan.eledger.adapter.out.epayment.persistence.rma;

import bhutan.eledger.application.port.out.epayment.rma.RmaMessageRepositoryPort;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class RmaMessageAdapter implements RmaMessageRepositoryPort {
    private final RmaMessageEntityRepository rmaMessageEntityRepository;
    private final RmaMessageMapper rmaMessageMapper;

    @Override
    public RmaMessage create(RmaMessage rmaMessage) {
        var rmaMessageEntity = rmaMessageEntityRepository.save(
                rmaMessageMapper.mapToEntity(rmaMessage)
        );

        return rmaMessageMapper.mapToDomain(rmaMessageEntity);
    }

    @Override
    public void update(RmaMessage rmaMessage) {
        rmaMessageEntityRepository.save(
                rmaMessageMapper.mapToEntity(rmaMessage)
        );

    }

    @Override
    public Optional<RmaMessage> readById(Long id) {
        return rmaMessageEntityRepository.findById(id)
                .map(rmaMessageMapper::mapToDomain);
    }

    @Override
    public Optional<RmaMessage> readByOrderNo(String orderNo) {
        return rmaMessageEntityRepository.findByOrderNo(orderNo)
                .map(rmaMessageMapper::mapToDomain);
    }
}
