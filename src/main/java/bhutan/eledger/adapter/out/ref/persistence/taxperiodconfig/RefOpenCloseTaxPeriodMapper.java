package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.OpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class RefOpenCloseTaxPeriodMapper {
    RefOpenCloseTaxPeriodEntity mapToEntity(RefOpenCloseTaxPeriodConfig refOpenCloseTaxPeriodConfig) {
        RefOpenCloseTaxPeriodEntity refOpenCloseTaxPeriodEntity =
                new RefOpenCloseTaxPeriodEntity(
                        refOpenCloseTaxPeriodConfig.getTaxTypeCode(),
                        refOpenCloseTaxPeriodConfig.getCalendarYear(),
                        refOpenCloseTaxPeriodConfig.getTaxPeriodTypeId(),
                        refOpenCloseTaxPeriodConfig.getTransactionTypeId(),
                        refOpenCloseTaxPeriodConfig.getYears(),
                        refOpenCloseTaxPeriodConfig.getMonth()
                );

        refOpenCloseTaxPeriodConfig.getRecords()
                .stream()
                .map(r ->
                        new RefOpenCloseTaxPeriodRecordEntity(
                                r.getId(),
                                r.getPeriodId(),
                                r.getPeriod(),
                                r.getPeriodStartDate(),
                                r.getPeriodEndDate()
                        )
                )
                .forEach(refOpenCloseTaxPeriodEntity::addToRecords);


        return refOpenCloseTaxPeriodEntity;
    }

    RefOpenCloseTaxPeriodConfig mapToDomain(RefOpenCloseTaxPeriodEntity entity) {
        return RefOpenCloseTaxPeriodConfig.withId(
                entity.getId(),
                entity.getGlAccountPartFullCode(),
                entity.getCalendarYear(),
                entity.getTaxPeriodTypeId(),
                entity.getTransactionTypeId(),
                entity.getMonth(),
                entity.getYears(),
                entity.getRecords()
                        .stream()
                        .map(record ->
                                OpenCloseTaxPeriodRecord.withoutId(
                                        record.getPeriodId(),
                                        record.getPeriod(),
                                        record.getPeriodStartDate(),
                                        record.getPeriodEndDate()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
