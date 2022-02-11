package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadTaxPeriodSegmentsUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodConfigRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenOpenCloseTaxPeriodService implements LoadGenOpenCloseTaxPeriodUseCase {

    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;
    private final LoadTaxPeriodSegmentsUseCase loadTaxPeriodSegmentsUseCase;
    private final ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;

    private static final String fortnight = " fortnight";

    @Override
    public RefOpenCloseTaxPeriod loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command) {
        log.trace("Loading open close tax period record with command: {}", command);

        var refOpenCloseTaxPeriodConfig =
                refOpenCloseTaxPeriodRepositoryPort.readByGlFullCodeYearTaxPeriodTransType(
                        command.getGlAccountPartFullCode(),
                        command.getCalendarYear(),
                        command.getTaxPeriodCode(),
                        command.getTransactionTypeId()
                );
        if (refOpenCloseTaxPeriodConfig.isPresent()) {
            return refOpenCloseTaxPeriodConfig.get();
        } else {
            return generate(command);
        }
    }


    public RefOpenCloseTaxPeriod generate(LoadGenOpenCloseTaxPeriodConfigCommand command) {
        log.trace("Generating open close tax period record with command: {}", command);
        var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(command.getTaxPeriodCode());

        Collection<RefTaxPeriodSegment> segments =
                loadTaxPeriodSegmentsUseCase.findByTaxPeriodTypeId(taxPeriodType.get().getId());

        Collection<RefOpenCloseTaxPeriodRecord> records = new LinkedList<>();
        int year = command.getCalendarYear();
        if (TaxPeriodType.MONTHLY.getValue().equals(command.getTaxPeriodCode())) {
            int monthIndex = 0;
            for (RefTaxPeriodSegment segment : segments) {
                monthIndex++;
                LocalDate openDate = LocalDate.of(year, monthIndex, 1).plusMonths(1);
                LocalDate closeDate = command.getYears() != null
                        ? YearMonth.of(openDate.plusYears(command.getYears()).getYear(), openDate.plusYears(command.getYears()).getMonth()).atEndOfMonth()
                        : YearMonth.of(openDate.plusMonths(command.getMonth() - 1).getYear(), openDate.plusMonths(command.getMonth() - 1).getMonth()).atEndOfMonth();
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                segment.getId(),
                                segment.getDescription(),
                                openDate,
                                closeDate

                        ));
            }

        } else if (TaxPeriodType.QUARTERLY.getValue().equals(command.getTaxPeriodCode())) {
            LocalDate openOfQuarter = command.getMonth() != null ? LocalDate.of(year, 4, 1) :
                    LocalDate.of(year, 4, 1).plusYears(command.getYears());
            LocalDate closeOfQuarter = openOfQuarter;
            for (RefTaxPeriodSegment segment : segments) {
                closeOfQuarter = command.getMonth() != null ? openOfQuarter.plusMonths(command.getMonth() - 1) : openOfQuarter.plusYears(command.getYears());
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                segment.getId(),
                                segment.getDescription(),
                                openOfQuarter,
                                YearMonth.of(closeOfQuarter.getYear(), closeOfQuarter.getMonth()).atEndOfMonth()
                        ));
                openOfQuarter = openOfQuarter.plusMonths(3);
            }

        }else if (TaxPeriodType.FORTNIGHTLY.getValue().equals(command.getTaxPeriodCode())) {
            int fortnightIndex = 1;
            LocalDate openOfFortnight;
            LocalDate closeOfFortnight;
            for (RefTaxPeriodSegment segment : segments) {
                fortnightIndex++;
                int fortnightFirstDay;
                int fortnightMonth;
                if (1 == (fortnightIndex % 2)) {
                    fortnightMonth = (fortnightIndex / 2) + 1;
                    fortnightFirstDay = 1;
                } else {
                    fortnightMonth = fortnightIndex / 2;
                    fortnightFirstDay = 16;
                }

                year = fortnightMonth == 13 ? year + 1 : year;
                fortnightMonth = fortnightMonth == 13 ? 1 : fortnightMonth;
                openOfFortnight = LocalDate.of(year, fortnightMonth, fortnightFirstDay);
                closeOfFortnight = command.getMonth()!=null?openOfFortnight.plusMonths(command.getMonth()).minusDays(1):
                        openOfFortnight.plusYears(command.getYears()).minusDays(1);
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                segment.getId(),
                                segment.getDescription(),
                                openOfFortnight,
                                closeOfFortnight
                        ));
            }
        }else if (TaxPeriodType.HALFYEARLY.getValue().equals(command.getTaxPeriodCode())) {
            int halfIndex = 0;
            LocalDate openOfHalf = LocalDate.of(year,1,1 );
            for (RefTaxPeriodSegment segment : segments) {
                halfIndex++;
                openOfHalf = openOfHalf.plusMonths(6);
                LocalDate closeOfHalf = command.getMonth()!=null?openOfHalf.plusMonths(command.getMonth()-1)
                        :openOfHalf.plusYears(command.getYears());
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                segment.getId(),
                                segment.getDescription(),
                                openOfHalf,
                                YearMonth.of(closeOfHalf.getYear(),closeOfHalf.getMonth()).atEndOfMonth()
                        ));
            }
        }else if (TaxPeriodType.YEARLY.getValue().equals(command.getTaxPeriodCode())) {
            RefTaxPeriodSegment segment = segments.iterator().next();
            LocalDate openYear = LocalDate.of(year, 1, 1).plusYears(1);
            LocalDate closeYear =command.getMonth()!=null?openYear.plusMonths(command.getMonth()-1)
                    :openYear.plusMonths(command.getYears());
            records.add(
                    RefOpenCloseTaxPeriodRecord.withoutId(
                            segment.getId(),
                            segment.getDescription(),
                            openYear,
                            YearMonth.of(closeYear.getYear(),closeYear.getMonth()).atEndOfMonth()
                    ));
        }

        return RefOpenCloseTaxPeriod.withoutId(
                command.getGlAccountPartFullCode(),
                command.getCalendarYear(),
                command.getTaxPeriodCode(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                records
        );
    }
}
