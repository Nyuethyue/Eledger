package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.LinkedList;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertTaxPeriodService implements UpsertTaxPeriodUseCase {

    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    void validate(UpsertTaxPeriodCommand command) {
        if (command.getValidFrom().isAfter(command.getValidTo())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("validFrom", "Valid from is after valid to:" + command.getValidFrom())
            );
        }

        if (command.getValidFrom().getYear() < command.getCalendarYear()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Year", "Valid from year less than calendar year")
            );
        }

        command.getRecords().forEach(r -> {
           if(r.getPeriodStart().isAfter(r.getPeriodEnd())) {
               throw new ViolationException(
                       new ValidationError()
                               .addViolation("periodStart", "Period start is after period end")
               );
           }
//            if(r.getPeriodEnd().isAfter(r.getFilingDueDate())) {
//                throw new ViolationException(
//                        new ValidationError()
//                                .addViolation("periodStart", "Period end is after filling date")
//                );
//            }
//            if(r.getFilingDueDate().isAfter(r.getPaymentDueDate())) {
//                throw new ViolationException(
//                        new ValidationError()
//                                .addViolation("filingDueDate", "filingDueDate end is after paymentDueDate date")
//                );
//            }
            if(r.getPaymentDueDate().isAfter(r.getFinePenaltyCalcStartDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("paymentDueDate", "paymentDueDate end is after FinePenaltyCalcStartDate date")
                );
            }

        });
    }

    @Override
    public Long upsert(@Valid UpsertTaxPeriodCommand command) {
        log.trace("Upserting TaxPeriod with command: {}", command);
        validate(command);

        RefTaxPeriodConfig refTaxPeriodConfig = mapCommandToRefTaxPeriodConfig(command);

        log.trace("Persisting TaxPeriod: {}", refTaxPeriodConfig);

        Long id = refTaxPeriodRepositoryPort.upsert(refTaxPeriodConfig);

        log.debug("TaxPeriod with id: {} successfully created.", id);

        return id;
    }

    private RefTaxPeriodConfig mapCommandToRefTaxPeriodConfig(UpsertTaxPeriodCommand command) {
        Collection<TaxPeriodRecord> records = new LinkedList<>();
        String periodName = "January";
        for (TaxPeriodRecordCommand tpc : command.getRecords()) {
            records.add(
                    TaxPeriodRecord.withoutId(
                            tpc.getPeriodId(),
                            periodName,
                            tpc.getPeriodStart(),
                            tpc.getPeriodEnd(),
                            tpc.getFilingDueDate(),
                            tpc.getPaymentDueDate(),
                            tpc.getInterestCalcStartDate(),
                            tpc.getFinePenaltyCalcStartDate(),
                            tpc.getValidFrom(),
                            tpc.getTaxTypeCode()
                    )
            );
        }
        return RefTaxPeriodConfig.withId(
                command.getId(),
                command.getTaxTypeCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getDueDateCountForReturnFiling(),
                command.getDueDateCountForPayment(),
                command.getValidFrom(),
                command.getValidTo(),
                command.getConsiderNonWorkingDays(),
                records
        );
    }
}
