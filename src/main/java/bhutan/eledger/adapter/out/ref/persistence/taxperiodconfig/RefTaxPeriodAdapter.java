package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@RequiredArgsConstructor
class RefTaxPeriodAdapter implements RefTaxPeriodRepositoryPort {

    private final RefTaxPeriodMapper refTaxPeriodMapper;

    private final ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;
    private final RefTaxPeriodSegmentEntityRepository refTaxPeriodSegmentEntityRepository;
    private final RefTaxPeriodConfigEntityRepository refTaxPeriodConfigEntityRepository;
    private final RefTaxPeriodRecordEntityRepository refTaxPeriodRecordEntityRepository;

    @Override
    public Long create(RefTaxPeriodConfig config) {
        var id = refTaxPeriodConfigEntityRepository.save(refTaxPeriodMapper.mapToEntity(config)).getId();
        config.getRecords().forEach(r ->
            refTaxPeriodRecordEntityRepository.save(refTaxPeriodMapper.mapToEntity(id, r))
        );
        return id;
    }

    @Override
    public Long update(RefTaxPeriodConfig config) {
        var id = refTaxPeriodConfigEntityRepository.save(refTaxPeriodMapper.mapToEntity(config)).getId();
        refTaxPeriodRecordEntityRepository.deleteByTaxPeriodConfigId(id);
        config.getRecords().forEach(r ->
            refTaxPeriodRecordEntityRepository.save(refTaxPeriodMapper.mapToEntity(id, r))
        );
        return id;
    }

    @Override
    public Optional<RefTaxPeriodConfig> readBy(String taxTypeCode, Integer calendarYear, String taxPeriodCode, Long transactionTypeId) {
        return refTaxPeriodConfigEntityRepository.readBy(taxTypeCode, calendarYear, taxPeriodCode, transactionTypeId)
                .map(taxPeriodConfig -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodCode());
                    var segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    var entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentList);
                });
    }

    @Override
    public SearchResult<RefTaxPeriodConfig> search(String taxTypeCode, Integer calendarYear, String taxPeriodCode, Long transactionTypeId) {
        return refTaxPeriodConfigEntityRepository.readBy(
                        taxTypeCode,
                        calendarYear,
                        taxPeriodCode,
                        transactionTypeId
                ).map(taxPeriodConfig -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodCode());
                    var segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    var entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return SearchResult.unpaged(Arrays.asList(refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentList)));
                })
                .orElse(SearchResult.unpaged(new ArrayList<>()));
    }


    @Override
    public void deleteAll() {
        refTaxPeriodRecordEntityRepository.deleteAll();
        refTaxPeriodConfigEntityRepository.deleteAll();
    }
}
