package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
class RefTaxPeriodMapper {

    RefTaxPeriodConfigEntity mapToEntity(RefTaxPeriodConfig refTaxPeriodConfig) {
        RefTaxPeriodConfigEntity refTaxPeriodConfigEntity =
                new RefTaxPeriodConfigEntity(
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

        refTaxPeriodConfigEntity.getRecords()
                .stream()
                .map(r ->
                        new RefTaxPeriodRecordEntity(
                                r.getId(),
                                r.getPeriodStartDate(),
                                r.getPeriodEndDate(),
                                r.getFilingDueDate(),
                                r.getPaymentDueDate(),
                                r.getInterestCalcStartDay(),
                                r.getFineAndPenaltyCalcStartDay(),
                                r.getValidFrom(),
                                r.getTaxTypeCode()
                        )
                )
                .forEach(refTaxPeriodConfigEntity::addToRecords);

        return refTaxPeriodConfigEntity;
    }

    RefTaxPeriodConfig mapToDomain(RefTaxPeriodConfigEntity entity) {
        List<TaxPeriodRecord> records = new LinkedList<>();
        entity.getRecords().stream().map(re ->
                records.add(
                        TaxPeriodRecord.withId(
                                re.getId(),
                                re.getPeriodId(),
                                re.getPeriodStartDate(),
                                re.getPeriodEndDate(),
                                re.getFilingDueDate(),
                                re.getPaymentDueDate(),
                                re.getInterestCalcStartDay(),
                                re.getFineAndPenaltyCalcStartDay(),
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
}
