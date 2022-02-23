package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodConfigRecord;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
class RefTaxPeriodMapper {

    RefTaxPeriodConfigEntity mapToEntity(long id, RefTaxPeriodConfig refTaxPeriodConfig) {
        RefTaxPeriodConfigEntity refTaxPeriodConfigEntity =
                RefTaxPeriodConfigEntity.withId(
                        id,
                        refTaxPeriodConfig.getTaxTypeCode(),
                        refTaxPeriodConfig.getCalendarYear(),
                        refTaxPeriodConfig.getTaxPeriodCode(),
                        refTaxPeriodConfig.getTransactionTypeId(),
                        refTaxPeriodConfig.getDueDateCountForReturnFiling(),
                        refTaxPeriodConfig.getDueDateCountForPayment(),
                        refTaxPeriodConfig.getValidFrom(),
                        refTaxPeriodConfig.getValidTo(),
                        refTaxPeriodConfig.getConsiderNonWorkingDays()
                );

        return refTaxPeriodConfigEntity;
    }

    RefTaxPeriodConfigEntity mapToEntity(RefTaxPeriodConfig refTaxPeriodConfig) {
        RefTaxPeriodConfigEntity refTaxPeriodConfigEntity =
                RefTaxPeriodConfigEntity.withoutId(
                        refTaxPeriodConfig.getTaxTypeCode(),
                        refTaxPeriodConfig.getCalendarYear(),
                        refTaxPeriodConfig.getTaxPeriodCode(),
                        refTaxPeriodConfig.getTransactionTypeId(),
                        refTaxPeriodConfig.getDueDateCountForReturnFiling(),
                        refTaxPeriodConfig.getDueDateCountForPayment(),
                        refTaxPeriodConfig.getValidFrom(),
                        refTaxPeriodConfig.getValidTo(),
                        refTaxPeriodConfig.getConsiderNonWorkingDays()
                );

        return refTaxPeriodConfigEntity;
    }

    RefTaxPeriodConfig mapToDomain(RefTaxPeriodConfigEntity entity,
                                   Collection<RefTaxPeriodRecordEntity> entityRecords,
                                   List<RefTaxPeriodSegment> segmentList) {
        List<TaxPeriodConfigRecord> records = new LinkedList<>();
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
        entityRecords.stream().forEach(re ->
                records.add(
                        TaxPeriodConfigRecord.withId(
                                re.getId(),
                                re.getPeriodSegmentId(),
                                segmentCodes.get(re.getPeriodSegmentId()),
                                segmentNames.get(re.getPeriodSegmentId()),
                                re.getPeriodStartDate(),
                                re.getPeriodEndDate(),
                                re.getFilingDueDate(),
                                re.getPaymentDueDate(),
                                re.getInterestCalcStartDate(),
                                re.getFineAndPenaltyCalcStartDate(),
                                re.getValidFrom(),
                                re.getTaxTypeCode()
                        )));
        return RefTaxPeriodConfig.withId(
                entity.getId(),
                entity.getGlAccountPartFullCode(),
                entity.getCalendarYear(),
                entity.getTaxPeriodCode(),
                entity.getTransactionTypeId(),
                entity.getDueDateCountForReturnFiling(),
                entity.getDueDateCountForPayment(),
                entity.getValidFrom(),
                entity.getValidTo(),
                entity.getConsiderNonWorkingDays(),
                records
        );
    }

    RefTaxPeriodRecordEntity mapToEntity(long parentId, TaxPeriodConfigRecord re) {
        return new RefTaxPeriodRecordEntity(
                parentId,
                re.getPeriodId(),
                re.getPeriodStartDate(),
                re.getPeriodEndDate(),
                re.getFilingDueDate(),
                re.getPaymentDueDate(),
                re.getInterestCalcStartDate(),
                re.getFineAndPenaltyCalcStartDate(),
                re.getValidFrom(),
                re.getTaxTypeCode()
        );
    }
}
