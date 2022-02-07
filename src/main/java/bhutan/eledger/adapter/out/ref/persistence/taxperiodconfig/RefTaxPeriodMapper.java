package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import org.springframework.stereotype.Component;

import java.time.MonthDay;
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
        entityRecords.stream().forEach(re ->
                records.add(
                        TaxPeriodRecord.withId(
                                re.getId(),
                                re.getPeriodId(),
                                entity.getCalendarYear(),
                                MonthDay.of(re.getPeriodStartMonth(), re.getPeriodStartDay()),
                                MonthDay.of(re.getPeriodEndMonth(), re.getPeriodEndDay()),
                                MonthDay.of(re.getFilingDueMonth(), re.getFilingDueDay()),
                                MonthDay.of(re.getPaymentDueMonth(), re.getPaymentDueDay()),
                                MonthDay.of(re.getInterestCalcStartMonth(), re.getInterestCalcStartDay()),
                                MonthDay.of(re.getFineAndPenaltyCalcStartMonth(), re.getFineAndPenaltyCalcStartDay()),

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

    RefTaxPeriodRecordEntity mapToEntity(long parentId, int year, TaxPeriodRecord re) {
        return new RefTaxPeriodRecordEntity(
                parentId,
                re.getPeriodId(),
                year,
                re.getPeriodStartDate().getMonthValue(),
                re.getPeriodStartDate().getDayOfMonth(),

                re.getPeriodEndDate().getMonthValue(),
                re.getPeriodEndDate().getDayOfMonth(),

                re.getFilingDueDate().getMonthValue(),
                re.getFilingDueDate().getDayOfMonth(),

                re.getPaymentDueDate().getMonthValue(),
                re.getPaymentDueDate().getDayOfMonth(),

                re.getInterestCalcStartDay().getMonthValue(),
                re.getInterestCalcStartDay().getDayOfMonth(),

                re.getFineAndPenaltyCalcStartDay().getMonthValue(),
                re.getFineAndPenaltyCalcStartDay().getDayOfMonth(),

                re.getValidFrom(),
                re.getTaxTypeCode()
        );
    }
}
