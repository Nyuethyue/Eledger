package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
@RequiredArgsConstructor
class RefTaxPeriodAdapter implements RefTaxPeriodRepositoryPort {

    private final RefTaxPeriodMapper refTaxPeriodMapper;

    private final ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;
    private final RefTaxPeriodSegmentEntityRepository refTaxPeriodSegmentEntityRepository;
    private final RefTaxPeriodConfigEntityRepository refTaxPeriodConfigEntityRepository;
    private final RefTaxPeriodRecordEntityRepository refTaxPeriodRecordEntityRepository;

    @Override
    public Long create(RefTaxPeriodConfig b) {
        var id = refTaxPeriodConfigEntityRepository.save(refTaxPeriodMapper.mapToEntity(b)).getId();
        b.getRecords().stream().forEach(r ->
                refTaxPeriodRecordEntityRepository.save(refTaxPeriodMapper.mapToEntity(id, r))
        );
        return id;
    }

    @Override
    public Long update(RefTaxPeriodConfig conf) {
        var id = refTaxPeriodConfigEntityRepository.save(refTaxPeriodMapper.mapToEntity(conf.getId(), conf)).getId();
        refTaxPeriodRecordEntityRepository.deleteByTaxPeriodConfigId(id);
        conf.getRecords().stream().forEach(r ->
                refTaxPeriodRecordEntityRepository.save(refTaxPeriodMapper.mapToEntity(id, r))
        );
        return id;
    }

    @Override
    public Optional<RefTaxPeriodConfig> readBy(String taxTypeCode, Integer calendarYear, String taxPeriodCode, Long transactionTypeId) {
        return refTaxPeriodConfigEntityRepository.readBy(taxTypeCode, calendarYear, taxPeriodCode, transactionTypeId)
                .map(taxPeriodConfig -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodCode());
                    var segments = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    Map<Long, Multilingual> segmentMap = new HashMap<>();
                    segments.forEach(segment -> segmentMap.put(segment.getId(), segment.getDescription()));
                    Collection<RefTaxPeriodRecordEntity> entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return Optional.of(refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentMap));
                }).orElse(Optional.empty());
    }

    @Override
    public void deleteAll() {
        refTaxPeriodRecordEntityRepository.deleteAll();
        refTaxPeriodConfigEntityRepository.deleteAll();
    }
}
