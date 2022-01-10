package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.application.port.out.epayment.deposit.reconciliation.ReconciliationUploadRecordRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class ReconciliationUploadRecordAdapter implements ReconciliationUploadRecordRepositoryPort {
    private final ReconciliationUploadRecordMapper mapper;
    private final ReconciliationUploadRecordEntityRepository repository;

    @Override
    public Optional<ReconciliationUploadRecordInfo> readById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToDomain);
    }

    @Override
    public Collection<ReconciliationUploadRecordInfo> readAll() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }


    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
