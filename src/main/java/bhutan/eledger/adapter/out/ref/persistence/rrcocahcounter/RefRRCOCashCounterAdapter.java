package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounter;

import bhutan.eledger.application.port.out.ref.rrcocashcounter.RefRRCOCashCounterRepositoryPort;
import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefRRCOCashCounterAdapter implements RefRRCOCashCounterRepositoryPort {

    private final RefRRCOCashCounterMapper refRRCOCashCounterMapper;
    private final RefRRCOCashCounterEntityRepository refRRCOCashCounterEntityRepository;

    @Override
    public Long create(RefRRCOCashCounter refRRCOCashCounter) {
        RefRRCOCashCounterEntity refRRCOCashCounterEntity =
                refRRCOCashCounterMapper.mapToEntity(refRRCOCashCounter);

        return refRRCOCashCounterEntityRepository.save(refRRCOCashCounterEntity).getId();
    }

    @Override
    public Collection<RefRRCOCashCounter> readAll() {
        return refRRCOCashCounterEntityRepository.findAll()
                .stream()
                .map(refRRCOCashCounterMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<RefRRCOCashCounter> readById(Long id) {
        return refRRCOCashCounterEntityRepository.findById(id)
                .map(refRRCOCashCounterMapper::mapToDomain);
    }

    @Override
    public Optional<RefRRCOCashCounter> readByCode(String code, LocalDate currentDate) {
        return refRRCOCashCounterEntityRepository.readByCode(code, currentDate)
                .map(refRRCOCashCounterMapper::mapToDomain);
    }

    @Override
    public void deleteAll() {
        refRRCOCashCounterEntityRepository.deleteAll();
    }

    @Override
    public boolean isOpenRRCOCashCountersExists(RefRRCOCashCounter refRRCOCashCounter) {
        return refRRCOCashCounterEntityRepository.existsByCodeAndEndOfValidityNullOrEndOfValidity(refRRCOCashCounter.getCode(), LocalDate.now(), refRRCOCashCounter.getValidityPeriod().getStart());
    }
}
