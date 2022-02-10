package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class RefOpenCloseTaxPeriodAdapter implements RefOpenCloseTaxPeriodRepositoryPort {
    private final RefOpenCloseTaxPeriodMapper refOpenCloseTaxPeriodMapper;
    private final RefOpenCloseTaxPeriodEntityRepository refOpenCloseTaxPeriodEntityRepository;

    @Override
    public Long create(RefOpenCloseTaxPeriod refOpenCloseTaxPeriodConfig) {
        RefOpenCloseTaxPeriodEntity refOpenCloseTaxPeriodEntity = refOpenCloseTaxPeriodEntityRepository.save(
                refOpenCloseTaxPeriodMapper.mapToEntity(refOpenCloseTaxPeriodConfig)
        );
        return refOpenCloseTaxPeriodEntity.getId();
    }

    @Override
    public Optional<RefOpenCloseTaxPeriod> readByGlFullCodeYearTaxPeriodTransType(String glAccountPartFullCode, Integer calendarYear, Long taxPeriodTypeId, Long transactionTypeId) {
        var result = refOpenCloseTaxPeriodEntityRepository.readBy(glAccountPartFullCode, calendarYear, taxPeriodTypeId, transactionTypeId);
        if(result.isPresent()) {
            return Optional.of(refOpenCloseTaxPeriodMapper.mapToDomain(result.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RefOpenCloseTaxPeriod> readById(Long id) {
        return refOpenCloseTaxPeriodEntityRepository.findById(id)
                .map(refOpenCloseTaxPeriodMapper::mapToDomain);
    }

    @Override
    public void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod) {
        refOpenCloseTaxPeriodEntityRepository.save(
                refOpenCloseTaxPeriodMapper.mapToEntity(refOpenCloseTaxPeriod)
        );
    }
}