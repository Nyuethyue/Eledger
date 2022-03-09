package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.PageableResolver;
import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.SearchTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    public void delete(Long id) {
        refTaxPeriodRecordEntityRepository.deleteByTaxPeriodConfigId(id);
        refTaxPeriodConfigEntityRepository.deleteById(id);
    }

    @Override
    public Optional<RefTaxPeriodConfig> findById(Long id) {
        return refTaxPeriodConfigEntityRepository.findById(id)
                .map(tp -> refTaxPeriodMapper.mapToDomain(tp));
    }

    @Override
    public Optional<RefTaxPeriodConfig> readBy(
            String taxTypeCode,
            Integer calendarYear,
            String taxPeriodCode,
            Long transactionTypeId,
            LocalDate validFrom,
            LocalDate validTo) {
        return refTaxPeriodConfigEntityRepository.readBy(
                taxTypeCode, calendarYear, taxPeriodCode, transactionTypeId, validFrom, validTo)
                .map(taxPeriodConfig -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodCode());
                    var segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    var entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentList);
                });
    }

    @Override
    public Collection<RefTaxPeriodConfig> searchAll(String taxTypeCode, Integer calendarYear, String taxPeriodCode, Long transactionTypeId) {
        return refTaxPeriodConfigEntityRepository.findAll(
                taxTypeCode,
                calendarYear,
                taxPeriodCode,
                transactionTypeId
        ).stream().map(taxPeriodConfig -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodCode());
                    var segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    var entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentList);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public SearchResult<RefTaxPeriodConfig> search(SearchTaxPeriodConfigUseCase.SearchTaxPeriodConfigCommand command) {
        Pageable pageable = PageableResolver.resolve(command);

        Page<RefTaxPeriodConfig> page = refTaxPeriodConfigEntityRepository.findAll(
                        querydsl -> resolveQuery(command, querydsl),
                        pageable
                )
                .map(taxPeriodConfig -> {
                    var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(taxPeriodConfig.getTaxPeriodCode());
                    var segmentList = refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodType.get().getId());
                    var entityRecords = refTaxPeriodRecordEntityRepository.readTaxPeriodRecords(taxPeriodConfig.getId());
                    return refTaxPeriodMapper.mapToDomain(taxPeriodConfig, entityRecords, segmentList);
                });

        return PagedSearchResult.of(page);
    }

    private JPQLQuery<RefTaxPeriodConfigEntity> resolveQuery(SearchTaxPeriodConfigUseCase.SearchTaxPeriodConfigCommand command, Querydsl querydsl) {
        var refTaxPeriodConfig = QRefTaxPeriodConfigEntity.refTaxPeriodConfigEntity;

        var jpqlQuery = querydsl
                .createQuery(refTaxPeriodConfig)
                .select(refTaxPeriodConfig);

        BooleanBuilder predicate = new BooleanBuilder(refTaxPeriodConfig.taxPeriodCode.eq(command.getTaxPeriodCode()));
        predicate.and(refTaxPeriodConfig.glAccountPartFullCode.eq(command.getTaxTypeCode()));
        predicate.and(refTaxPeriodConfig.calendarYear.eq(command.getCalendarYear()));
        predicate.and(refTaxPeriodConfig.transactionTypeId.eq(command.getTransactionTypeId()));

        return jpqlQuery.where(predicate);
    }

    @Override
    public void deleteAll() {
        refTaxPeriodRecordEntityRepository.deleteAll();
        refTaxPeriodConfigEntityRepository.deleteAll();
    }
}
