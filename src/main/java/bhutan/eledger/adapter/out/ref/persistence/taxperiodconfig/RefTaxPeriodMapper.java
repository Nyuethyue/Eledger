package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
class RefTaxPeriodMapper {

    RefTaxPeriodConfigEntity mapToEntity(long id, RefTaxPeriodConfig refTaxPeriodConfig) {
        RefTaxPeriodConfigEntity refTaxPeriodConfigEntity =
                RefTaxPeriodConfigEntity.withId(
                        id,
                        refTaxPeriodConfig.getTaxTypeCode(),
                        refTaxPeriodConfig.getCalendarYear(),
                        refTaxPeriodConfig.getTaxPeriodTypeId(),
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
                        refTaxPeriodConfig.getTaxPeriodTypeId(),
                        refTaxPeriodConfig.getTransactionTypeId(),
                        refTaxPeriodConfig.getDueDateCountForReturnFiling(),
                        refTaxPeriodConfig.getDueDateCountForPayment(),
                        refTaxPeriodConfig.getValidFrom(),
                        refTaxPeriodConfig.getValidTo(),
                        refTaxPeriodConfig.getConsiderNonWorkingDays()
                );

        return refTaxPeriodConfigEntity;
    }

    RefTaxPeriodConfig mapToDomain(RefTaxPeriodConfigEntity entity, Collection<RefTaxPeriodRecordEntity> entityRecords) {
        List<TaxPeriodRecord> records = new LinkedList<>();
        String periodName = "January";
        entityRecords.stream().forEach(re ->
                records.add(
                        TaxPeriodRecord.withId(
                                re.getId(),
                                re.getPeriodId(),
                                periodName,
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
                entity.getTaxPeriodTypeId(),
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
