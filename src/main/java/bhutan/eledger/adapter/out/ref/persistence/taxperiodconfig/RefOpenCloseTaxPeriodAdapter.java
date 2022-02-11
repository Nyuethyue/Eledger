package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class RefOpenCloseTaxPeriodAdapter implements RefOpenCloseTaxPeriodRepositoryPort {
    private final RefOpenCloseTaxPeriodMapper refOpenCloseTaxPeriodMapper;
    private final RefOpenCloseTaxPeriodEntityRepository refOpenCloseTaxPeriodEntityRepository;
    private final RefTaxPeriodSegmentEntityRepository refTaxPeriodSegmentEntityRepository;
    private final ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;

    @Override
    public Long create(RefOpenCloseTaxPeriod refOpenCloseTaxPeriodConfig) {
        RefOpenCloseTaxPeriodEntity refOpenCloseTaxPeriodEntity = refOpenCloseTaxPeriodEntityRepository.save(
                refOpenCloseTaxPeriodMapper.mapToEntity(refOpenCloseTaxPeriodConfig)
        );
        return refOpenCloseTaxPeriodEntity.getId();
    }

    @Override
    public Optional<RefOpenCloseTaxPeriod> readByGlFullCodeYearTaxPeriodTransType(String glAccountPartFullCode, Integer calendarYear, String taxPeriodCode, Long transactionTypeId) {
        return refOpenCloseTaxPeriodEntityRepository.readBy(glAccountPartFullCode, calendarYear, taxPeriodCode, transactionTypeId)
                .map(res -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(res.getTaxPeriodCode());
                    var segments = refTaxPeriodSegmentEntityRepository.findByTaxPeriodTypeIdOrderByCodeAsc(taxPeriodType.get().getId());
                    Map<Long, Multilingual> segmentMap = new HashMap<>();
                    segments.forEach(segment -> segmentMap.put(segment.getId(), segment.getDescription()));
                    return Optional.of(refOpenCloseTaxPeriodMapper.mapToDomain(res, segmentMap));
                }).orElse(Optional.empty());
    }

    @Override
    public Optional<RefOpenCloseTaxPeriod> readById(Long id) {
        return refOpenCloseTaxPeriodEntityRepository.findById(id)
                .map(res -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(res.getTaxPeriodCode());
                    var segments = refTaxPeriodSegmentEntityRepository.findByTaxPeriodTypeIdOrderByCodeAsc(taxPeriodType.get().getId());
                    Map<Long, Multilingual> segmentMap = new HashMap<>();
                    segments.forEach(segment -> segmentMap.put(segment.getId(), segment.getDescription()));
                    return Optional.of(refOpenCloseTaxPeriodMapper.mapToDomain(res, segmentMap));
                }).orElse(Optional.empty());
    }

    @Override
    public void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod) {
        refOpenCloseTaxPeriodEntityRepository.save(
                refOpenCloseTaxPeriodMapper.mapToEntity(refOpenCloseTaxPeriod)
        );
    }
}