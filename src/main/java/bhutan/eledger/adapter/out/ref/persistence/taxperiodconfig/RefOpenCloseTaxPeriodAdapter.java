package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
                    List<RefTaxPeriodSegment> segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    return refOpenCloseTaxPeriodMapper.mapToDomain(res, segmentList);
                });
    }

    @Override
    public Optional<RefOpenCloseTaxPeriod> readById(Long id) {
        return refOpenCloseTaxPeriodEntityRepository.findById(id)
                .map(res -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(res.getTaxPeriodCode());
                    List<RefTaxPeriodSegment> segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    return refOpenCloseTaxPeriodMapper.mapToDomain(res, segmentList);
                });
    }

    @Override
    public void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod) {
        refOpenCloseTaxPeriodEntityRepository.save(
                refOpenCloseTaxPeriodMapper.mapToEntity(refOpenCloseTaxPeriod)
        );
    }
}