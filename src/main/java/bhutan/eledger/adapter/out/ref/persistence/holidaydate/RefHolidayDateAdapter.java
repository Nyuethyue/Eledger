package bhutan.eledger.adapter.out.ref.persistence.holidaydate;

import bhutan.eledger.application.port.out.ref.holidaydate.RefHolidayDateRepositoryPort;
import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefHolidayDateAdapter implements RefHolidayDateRepositoryPort {
    private final RefHolidayDateMapper refHolidayDateMapper;
    private final RefHolidayDateEntityRepository refHolidayDateEntityRepository;

    @Override
    public Collection<RefHolidayDate> create(Collection<RefHolidayDate> holidayDates) {
        return refHolidayDateEntityRepository.saveAll(
                        holidayDates
                                .stream()
                                .map(refHolidayDateMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(refHolidayDateMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<RefHolidayDate> readAll() {
        return refHolidayDateEntityRepository.findAll()
                .stream()
                .map(refHolidayDateMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refHolidayDateEntityRepository.deleteAll();
    }

    @Override
    public Optional<RefHolidayDate> readById(Long id) {
        return refHolidayDateEntityRepository.findById(id)
                .map(refHolidayDateMapper::mapToDomain);
    }
}
