package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodConfigRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.Days;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedList;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertTaxPeriodService implements UpsertTaxPeriodUseCase {

    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    void validate(UpsertTaxPeriodCommand command) {
        try {
            TaxPeriodType.of(command.getTaxPeriodCode());
        } catch (Exception e) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("taxPeriodCode",
                                    "Invalid tax period type code:" + command.getTaxPeriodCode()));

        }

        if (null != command.getValidTo() && command.getValidFrom().isAfter(command.getValidTo())) {
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
            LocalDate periodEnd = r.getPeriodEnd();

            if (364 < ChronoUnit.DAYS.between(periodEnd, r.getFilingDueDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("fillingDueDate", "Invalid Filling Due Date, difference in days must be less than 365")
                );
            }

            if (364 < ChronoUnit.DAYS.between(periodEnd, r.getPaymentDueDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("paymentDueDate", "Invalid Payment Due Date, difference in days must be less than 365")
                );
            }

            if (364 < ChronoUnit.DAYS.between(periodEnd, r.getFinePenaltyCalcStartDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("finePenaltyCalcStartDate", "Invalid Fine Penalty Calc Start Date, difference in days must be less than 365")
                );
            }

            if (364 < ChronoUnit.DAYS.between(periodEnd, r.getInterestCalcStartDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("interestCalcStartDate", "Invalid Interest Calc Start Date, difference in days must be less than 365")
                );
            }

            if (r.getPaymentDueDate().isAfter(r.getFinePenaltyCalcStartDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("paymentDueDate", "paymentDueDate end is after finePenaltyCalcStartDate date")
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

        var conf =
                refTaxPeriodRepositoryPort.readBy(refTaxPeriodConfig.getTaxTypeCode(),
                                    refTaxPeriodConfig.getCalendarYear(),
                                    refTaxPeriodConfig.getTaxPeriodCode(),
                                    refTaxPeriodConfig.getTransactionTypeId());
        Long id;
        if(conf.isPresent()) {
            id = refTaxPeriodRepositoryPort.update(refTaxPeriodConfig);
        } else {
            id = refTaxPeriodRepositoryPort.create(refTaxPeriodConfig);
        }

        log.debug("TaxPeriod with id: {} successfully created.", id);

        return id;
    }

    private RefTaxPeriodConfig mapCommandToRefTaxPeriodConfig(UpsertTaxPeriodCommand command) {
        Collection<TaxPeriodConfigRecord> records = new LinkedList<>();
        for (TaxPeriodRecordCommand tpc : command.getRecords()) {
            records.add(
                    TaxPeriodConfigRecord.withoutId(
                            tpc.getPeriodSegmentId(),
                            null,
                            null,
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
                command.getTaxPeriodCode(),
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
