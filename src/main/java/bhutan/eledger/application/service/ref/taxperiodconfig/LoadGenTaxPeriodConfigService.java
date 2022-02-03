package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.nonworkingdays.ReadRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenTaxPeriodConfigService implements LoadGenTaxPeriodConfigUseCase {
    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;
    private final ReadRefNonWorkingDaysUseCase readRefNonWorkingDaysUseCase;

    @Override
    public RefTaxPeriodConfig loadGen(@Valid LoadGenTaxPeriodConfigCommand command) {
        var refTaxPeriodConfig =
                refTaxPeriodRepositoryPort.readBy(
                        command.getTaxTypeCode(),
                        command.getCalendarYear(),
                        command.getTaxPeriodTypeId(),
                        command.getTransactionTypeId()
                );
        if (refTaxPeriodConfig.isPresent()) {
            return refTaxPeriodConfig.get();
        } else {
            return generate(command);
        }
    }

    private static final long MONTHLY = 1;// 12 rows
    private static final long QUARTERLY = 2; // 4 rows
    private static final long FORTNIGHTLY = 3; // 24 rows

    public RefTaxPeriodConfig generate(LoadGenTaxPeriodConfigCommand command) {
        int year = command.getCalendarYear();
        Set<Integer> nonWorkingDays = loadNonWorkingDays(year);
        Collection<TaxPeriodRecord> records = new LinkedList<>();
        boolean consider = command.getConsiderNonWorkingDays();
        if (MONTHLY == command.getTaxPeriodTypeId()) {
            for (int monthIndex = 1; monthIndex <= 12; monthIndex++) {
                LocalDate endOfMonth = YearMonth.of(year, monthIndex).atEndOfMonth();
                records.add(
                        TaxPeriodRecord.withoutId(
                                monthIndex,
                                LocalDate.of(year, monthIndex, 1),// Start period
                                endOfMonth,// End period
                                addDays(nonWorkingDays, endOfMonth, command.getDueDateCountForReturnFiling(), consider),
                                addDays(nonWorkingDays, endOfMonth, command.getDueDateCountForPayment(), consider),
                                addDays(nonWorkingDays, endOfMonth, command.getDueDateCountForReturnFiling() + 1, consider),
                                addDays(nonWorkingDays, endOfMonth, command.getDueDateCountForPayment() + 1, consider),
                                command.getValidFrom(),
                                command.getTaxTypeCode()
                        ));
            }
        } else if (QUARTERLY == command.getTaxPeriodTypeId()) {
            for (int quarterIndex = 1; quarterIndex <= 4; quarterIndex++) {
                LocalDate endOfQuarter = YearMonth.of(year, 3 * quarterIndex).atEndOfMonth();
                records.add(
                        TaxPeriodRecord.withoutId(
                                quarterIndex,
                                LocalDate.of(year, (3 * quarterIndex) - 2, 1),// Start period
                                endOfQuarter,// End period
                                addDays(nonWorkingDays, endOfQuarter, command.getDueDateCountForReturnFiling(), consider),
                                addDays(nonWorkingDays, endOfQuarter, command.getDueDateCountForPayment(), consider),
                                addDays(nonWorkingDays, endOfQuarter, command.getDueDateCountForReturnFiling() + 1, consider),
                                addDays(nonWorkingDays, endOfQuarter, command.getDueDateCountForPayment() + 1, consider),
                                command.getValidFrom(),
                                command.getTaxTypeCode()
                        ));
            }
        } else if (FORTNIGHTLY == command.getTaxPeriodTypeId()) {
            LocalDate endOfFortnight;
            for (int fortnightIndex = 1; fortnightIndex <= 24; fortnightIndex++) {
                int fortnightFirstDay;
                int fortnightMonth;
                if (1 == (fortnightIndex % 2)) {
                    fortnightMonth = (fortnightIndex / 2) + 1;
                    fortnightFirstDay = 1;
                    endOfFortnight = LocalDate.of(year, fortnightMonth, 15);
                } else {
                    fortnightMonth = fortnightIndex / 2;
                    fortnightFirstDay = 15;
                    endOfFortnight = YearMonth.of(year, fortnightMonth).atEndOfMonth();
                }
                records.add(
                        TaxPeriodRecord.withoutId(
                                fortnightIndex,
                                LocalDate.of(year, fortnightMonth, fortnightFirstDay),// Start period
                                endOfFortnight,// End period
                                addDays(nonWorkingDays, endOfFortnight, command.getDueDateCountForReturnFiling(), consider),
                                addDays(nonWorkingDays, endOfFortnight, command.getDueDateCountForPayment(), consider),
                                addDays(nonWorkingDays, endOfFortnight, command.getDueDateCountForReturnFiling() + 1, consider),
                                addDays(nonWorkingDays, endOfFortnight, command.getDueDateCountForPayment() + 1, consider),
                                command.getValidFrom(),
                                command.getTaxTypeCode()
                        ));
            }
        }

        return RefTaxPeriodConfig.withoutId(
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

    private Set<Integer> loadNonWorkingDays(int year) {
        Set<Integer> result = new HashSet<>();
        Collection<RefNonWorkingDays> nwDays = readRefNonWorkingDaysUseCase.readAll();
        nwDays.stream().forEach(d -> {
            int sd = d.getStartDay().atYear(year).getDayOfYear();
            int ed = d.getEndDay().atYear(year).getDayOfYear();
            if (sd <= ed) {
                for (int i = sd; i < ed; i++) {
                    result.add(i);
                }
            }
        });
        return result;
    }

    private LocalDate addDays(Set<Integer> nonWorkingDays, LocalDate dateFrom, int dayCount, boolean considerNonWorkingDays) {
        LocalDate dateTo = dateFrom.plusDays(dayCount);
        int startDay = dateFrom.getDayOfYear();
        int endDay = dateTo.getDayOfYear();
        int numberOfNonWorkingDays;
        if (startDay <= endDay) {
            numberOfNonWorkingDays = countNWDays(nonWorkingDays, startDay, endDay);
        } else {
            int middleDay = dateFrom.lengthOfYear();
            numberOfNonWorkingDays = countNWDays(nonWorkingDays, startDay, middleDay);
            numberOfNonWorkingDays += countNWDays(nonWorkingDays, 1, endDay);
        }
        return dateFrom.plusDays(dayCount + numberOfNonWorkingDays);
    }

    private int countNWDays(Set<Integer> nonWorkingDays, int startDay, int endDay) {
        int numberOfNonWorkingDays = 0;
        for (int day = startDay; day < endDay; day++) {
            if (nonWorkingDays.contains(day)) {
                numberOfNonWorkingDays++;
            }
        }
        return numberOfNonWorkingDays;
    }
}
