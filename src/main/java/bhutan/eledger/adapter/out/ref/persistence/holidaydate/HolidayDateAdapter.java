package bhutan.eledger.adapter.out.ref.persistence.holidaydate;

import bhutan.eledger.application.port.out.ref.holidaydate.HolidayDateRepositoryPort;
import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class HolidayDateAdapter implements HolidayDateRepositoryPort {
    private final HolidayDateMapper holidayDateMapper;
    private final HolidayDateEntityRepository holidayDateEntityRepository;

    @Override
    public Collection<HolidayDate> create(Collection<HolidayDate> holidayDates) {
        return holidayDateEntityRepository.saveAll(
                        holidayDates
                                .stream()
                                .map(holidayDateMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(holidayDateMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<HolidayDate> readAll() {
        return holidayDateEntityRepository.findAll()
                .stream()
                .map(holidayDateMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        holidayDateEntityRepository.deleteAll();
    }

    @Override
    public Optional<HolidayDate> readById(Long id) {
        return holidayDateEntityRepository.findById(id)
                .map(holidayDateMapper::mapToDomain);
    }
}
