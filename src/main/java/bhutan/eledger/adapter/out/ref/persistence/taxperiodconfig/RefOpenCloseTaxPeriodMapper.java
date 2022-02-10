package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class RefOpenCloseTaxPeriodMapper {
    RefOpenCloseTaxPeriodEntity mapToEntity(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod) {
        RefOpenCloseTaxPeriodEntity refOpenCloseTaxPeriodEntity =
                new RefOpenCloseTaxPeriodEntity(
                        refOpenCloseTaxPeriod.getId(),
                        refOpenCloseTaxPeriod.getGlAccountFullCode(),
                        refOpenCloseTaxPeriod.getCalendarYear(),
                        refOpenCloseTaxPeriod.getTaxPeriodTypeId(),
                        refOpenCloseTaxPeriod.getTransactionTypeId(),
                        refOpenCloseTaxPeriod.getNoOfYears(),
                        refOpenCloseTaxPeriod.getNoOfMonth()
                );

        refOpenCloseTaxPeriod.getRecords()
                .stream()
                .map(r ->
                        new RefOpenCloseTaxPeriodRecordEntity(
                                r.getId(),
                                r.getPeriodId(),
                                r.getPeriod(),
                                r.getPeriodOpenDate(),
                                r.getPeriodCloseDate()
                        )
                )
                .forEach(refOpenCloseTaxPeriodEntity::addToRecords);


        return refOpenCloseTaxPeriodEntity;
    }

    RefOpenCloseTaxPeriod mapToDomain(RefOpenCloseTaxPeriodEntity entity) {
        return RefOpenCloseTaxPeriod.withId(
                entity.getId(),
                entity.getGlAccountPartFullCode(),
                entity.getCalendarYear(),
                entity.getTaxPeriodTypeId(),
                entity.getTransactionTypeId(),
                entity.getYearsNo(),
                entity.getMonth(),
                entity.getRecords()
                        .stream()
                        .map(record ->
                                RefOpenCloseTaxPeriodRecord.withId(
                                        record.getId(),
                                        record.getPeriodId(),
                                        record.getPeriod(),
                                        record.getPeriodOpenDate(),
                                        record.getPeriodCloseDate()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
