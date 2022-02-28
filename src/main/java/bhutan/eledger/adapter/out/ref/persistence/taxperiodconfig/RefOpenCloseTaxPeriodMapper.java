package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class RefOpenCloseTaxPeriodMapper {
    RefOpenCloseTaxPeriodEntity mapToEntity(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod) {
        RefOpenCloseTaxPeriodEntity refOpenCloseTaxPeriodEntity =
                new RefOpenCloseTaxPeriodEntity(
                        refOpenCloseTaxPeriod.getId(),
                        refOpenCloseTaxPeriod.getGlAccountPartFullCode(),
                        refOpenCloseTaxPeriod.getCalendarYear(),
                        refOpenCloseTaxPeriod.getTaxPeriodCode(),
                        refOpenCloseTaxPeriod.getTransactionTypeId(),
                        refOpenCloseTaxPeriod.getNoOfYears(),
                        refOpenCloseTaxPeriod.getNoOfMonth()
                );

        refOpenCloseTaxPeriod.getRecords()
                .stream()
                .map(r ->
                        new RefOpenCloseTaxPeriodRecordEntity(
                                r.getId(),
                                r.getPeriodSegmentId(),
                                r.getPeriodOpenDate(),
                                r.getPeriodCloseDate()
                        )
                )
                .forEach(refOpenCloseTaxPeriodEntity::addToRecords);


        return refOpenCloseTaxPeriodEntity;
    }

    RefOpenCloseTaxPeriod mapToDomain(RefOpenCloseTaxPeriodEntity entity, List<RefTaxPeriodSegment> segmentList) {
        Map<Long, Multilingual> segmentNames = segmentList
                .stream()
                .collect(
                        Collectors.toMap(RefTaxPeriodSegment::getId, RefTaxPeriodSegment::getDescription)
                );
        Map<Long, String> segmentCodes = segmentList
                .stream()
                .collect(
                        Collectors.toMap(RefTaxPeriodSegment::getId, RefTaxPeriodSegment::getCode)
                );

        return RefOpenCloseTaxPeriod.withId(
                entity.getId(),
                entity.getGlAccountPartFullCode(),
                entity.getCalendarYear(),
                entity.getTaxPeriodCode(),
                entity.getTransactionTypeId(),
                entity.getYearsNo(),
                entity.getMonth(),
                entity.getRecords()
                        .stream()
                        .map(record ->
                                RefOpenCloseTaxPeriodRecord.withId(
                                        record.getId(),
                                        record.getPeriodSegmentId(),
                                        segmentCodes.get(record.getPeriodSegmentId()),
                                        segmentNames.get(record.getPeriodSegmentId()),
                                        record.getPeriodOpenDate(),
                                        record.getPeriodCloseDate()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
