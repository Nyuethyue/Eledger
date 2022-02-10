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
    public Long upsert(RefTaxPeriodConfig b) {
        var conf =
                readBy(b.getTaxTypeCode(), b.getCalendarYear(), b.getTaxPeriodTypeCode(), b.getTransactionTypeId());
        if(conf.isPresent()) {
            var id = refTaxPeriodConfigEntityRepository.save(refTaxPeriodMapper.mapToEntity(conf.get().getId(), b)).getId();
            refTaxPeriodRecordEntityRepository.deleteByTaxPeriodConfigId(id);
            b.getRecords().stream().forEach(r ->
                refTaxPeriodRecordEntityRepository.save(refTaxPeriodMapper.mapToEntity(id, r))
            );
            return id;

        } else {
            var id = refTaxPeriodConfigEntityRepository.save(refTaxPeriodMapper.mapToEntity(b)).getId();
            b.getRecords().stream().forEach(r ->
                refTaxPeriodRecordEntityRepository.save(refTaxPeriodMapper.mapToEntity(id, r))
            );
            return id;
        }
    }

    @Override
    public Optional<RefTaxPeriodConfig> readBy(String taxTypeCode, Integer calendarYear, String taxPeriodTypeCode, Long transactionTypeId) {
        var result =
                refTaxPeriodConfigEntityRepository.readBy(taxTypeCode, calendarYear, taxPeriodTypeCode, transactionTypeId);
        if(result.isPresent()) {
            var taxPeriodConfig = result.get();
            var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodTypeCode());
            var segments = refTaxPeriodSegmentEntityRepository.findByTaxPeriodTypeIdOrderByCodeAsc(taxPeriodType.get().getId());
            Map<Long, Multilingual> segmentMap = new HashMap<>();
            segments.forEach(segment ->  segmentMap.put(segment.getId(), segment.getDescription()));
            Collection<RefTaxPeriodRecordEntity> entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(result.get().getId());
            return Optional.of(refTaxPeriodMapper.mapToDomain(result.get(),  entityRecords, segmentMap));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {

    }
}
