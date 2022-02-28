package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


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
                    List<RefTaxPeriodSegment> segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    Collection<RefTaxPeriodRecordEntity> entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentList);
                });
    }

    @Override
    public void deleteAll() {
        refTaxPeriodRecordEntityRepository.deleteAll();
        refTaxPeriodConfigEntityRepository.deleteAll();
    }
}
