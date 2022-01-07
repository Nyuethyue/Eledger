package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.application.port.out.epayment.deposit.reconciliation.ReconciliationUploadFileRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class ReconciliationUploadFileAdapter implements ReconciliationUploadFileRepositoryPort {
    private final ReconciliationUploadFileMapper mapper;
    private final ReconciliationUploadFileEntityRepository reconciliationUploadFileEntityRepository;

    @Override
    public Optional<ReconciliationUploadFileInfo> readById(Long id) {
        return reconciliationUploadFileEntityRepository.findById(id)
                .map(mapper::mapToDomain);
    }

    @Override
    public Collection<ReconciliationUploadFileInfo> readAll() {
        return reconciliationUploadFileEntityRepository.findAll()
                .stream()
                .map(mapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ReconciliationUploadFileInfo create(ReconciliationUploadFileInfo info) {
        ReconciliationUploadFileEntity entity = reconciliationUploadFileEntityRepository.save(
                mapper.mapToEntity(info)
        );

        return mapper.mapToDomain(entity);
    }

    @Override
    public void deleteAll() {
        reconciliationUploadFileEntityRepository.deleteAll();
    }
}
