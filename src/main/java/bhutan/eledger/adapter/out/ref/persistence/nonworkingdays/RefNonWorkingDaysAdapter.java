package bhutan.eledger.adapter.out.ref.persistence.nonworkingdays;

import bhutan.eledger.application.port.out.ref.nonworkingdays.RefNonWorkingDaysRepositoryPort;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefNonWorkingDaysAdapter implements RefNonWorkingDaysRepositoryPort {
    private final RefNonWorkingDaysMapper refNonWorkingDaysMapper;
    private final RefNonWorkingDaysEntityRepository refNonWorkingDaysEntityRepository;

    @Override
    public Collection<RefNonWorkingDays> create(Collection<RefNonWorkingDays> nonWorkingDays) {
        return refNonWorkingDaysEntityRepository.saveAll(
                        nonWorkingDays
                                .stream()
                                .map(refNonWorkingDaysMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(refNonWorkingDaysMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<RefNonWorkingDays> readAll() {
        return refNonWorkingDaysEntityRepository.findAll()
                .stream()
                .map(refNonWorkingDaysMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refNonWorkingDaysEntityRepository.deleteAll();
    }

    @Override
    public Optional<RefNonWorkingDays> readById(Long id) {
        return refNonWorkingDaysEntityRepository.findById(id)
                .map(refNonWorkingDaysMapper::mapToDomain);
    }
}
