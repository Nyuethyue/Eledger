package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class RefTaxPeriodMapper {

    RefTaxPeriodConfigEntity mapToEntity(long id, RefTaxPeriodConfig refTaxPeriodConfig) {
        RefTaxPeriodConfigEntity refTaxPeriodConfigEntity =
                RefTaxPeriodConfigEntity.withId(
                        id,
                        refTaxPeriodConfig.getTaxTypeCode(),
                        refTaxPeriodConfig.getCalendarYear(),
                        refTaxPeriodConfig.getTaxPeriodTypeCode(),
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
                        refTaxPeriodConfig.getTaxPeriodTypeCode(),
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
                                   Map<Long, Multilingual> segmentNames) {
        List<TaxPeriodRecord> records = new LinkedList<>();
        entityRecords.stream().forEach(re ->
                records.add(
                        TaxPeriodRecord.withId(
                                re.getId(),
                                re.getPeriodSegmentId(),
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
                entity.getTaxPeriodTypeCode(),
                entity.getTransactionTypeId(),
                entity.getDueDateCountForReturnFiling(),
                entity.getDueDateCountForPayment(),
                entity.getValidFrom(),
                entity.getValidTo(),
                entity.getConsiderNonWorkingDays(),
                records
        );
    }

    RefTaxPeriodRecordEntity mapToEntity(long parentId, TaxPeriodRecord re) {
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
