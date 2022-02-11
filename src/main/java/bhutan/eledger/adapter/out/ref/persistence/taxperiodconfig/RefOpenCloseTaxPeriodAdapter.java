package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    Map<Long, Multilingual> segmentMap = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId())
                            .stream()
                            .collect(
                                    Collectors.toMap(RefTaxPeriodSegment::getId, RefTaxPeriodSegment::getDescription)
                            );
                    return refOpenCloseTaxPeriodMapper.mapToDomain(res, segmentMap);
                });
    }

    @Override
    public Optional<RefOpenCloseTaxPeriod> readById(Long id) {
        return refOpenCloseTaxPeriodEntityRepository.findById(id)
                .map(res -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(res.getTaxPeriodCode());
                    Map<Long, Multilingual> segmentMap = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId())
                            .stream()
                            .collect(
                                    Collectors.toMap(RefTaxPeriodSegment::getId, RefTaxPeriodSegment::getDescription)
                            );
                    return refOpenCloseTaxPeriodMapper.mapToDomain(res, segmentMap);
                });
    }

    @Override
    public void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod) {
        refOpenCloseTaxPeriodEntityRepository.save(
                refOpenCloseTaxPeriodMapper.mapToEntity(refOpenCloseTaxPeriod)
        );
    }
}