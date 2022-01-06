package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounters;

import bhutan.eledger.application.port.out.ref.rrcocashcounters.RefRRCOCashCountersRepositoryPort;
import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefRRCOCashCountersAdapter implements RefRRCOCashCountersRepositoryPort {

    private final RefRRCOCashCountersMapper refRRCOCashCountersMapper;
    private final RefRRCOCashCountersEntityRepository refRRCOCashCountersEntityRepository;

    @Override
    public Long create(RefRRCOCashCounters refRRCOCashCounters) {
        RefRRCOCashCountersEntity refRRCOCashCountersEntity =
                refRRCOCashCountersMapper.mapToEntity(refRRCOCashCounters);

        return refRRCOCashCountersEntityRepository.save(refRRCOCashCountersEntity).getId();
    }

    @Override
    public Collection<RefRRCOCashCounters> readAll() {
        return refRRCOCashCountersEntityRepository.findAll()
                .stream()
                .map(refRRCOCashCountersMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<RefRRCOCashCounters> readById(Long id) {
        return refRRCOCashCountersEntityRepository.findById(id)
                .map(refRRCOCashCountersMapper::mapToDomain);
    }

    @Override
    public Optional<RefRRCOCashCounters> readByCode(String code, LocalDate currentDate) {
        return refRRCOCashCountersEntityRepository.readByCode(code, currentDate)
                .map(refRRCOCashCountersMapper::mapToDomain);
    }

    @Override
    public void deleteAll() {
        refRRCOCashCountersEntityRepository.deleteAll();
    }

    @Override
    public boolean isOpenRRCOCashCountersExists(RefRRCOCashCounters refRRCOCashCounters) {
        return refRRCOCashCountersEntityRepository.existsByCodeAndEndOfValidityNullOrEndOfValidity(refRRCOCashCounters.getCode(), LocalDate.now(), refRRCOCashCounters.getValidityPeriod().getStart());
    }
}
