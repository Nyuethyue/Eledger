package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.LinkedList;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenOpenCloseTaxPeriodService implements LoadGenOpenCloseTaxPeriodUseCase {

    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;

    private static final long MONTHLY = 1;// 12 rows
    private static final long QUARTERLY = 2; // 4 rows
    private static final long FORTNIGHTLY = 3; // 24 rows

    private static final String quarter = " quarter";
    private static final String fortnight = " fortnight";

    @Override
    public RefOpenCloseTaxPeriod loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command) {

        if (command.getGlAccountPartFullCode() == null || command.getCalendarYear() == null ||
                command.getTaxPeriodTypeId() == null || command.getTransactionTypeId() == null) {
            return generate(command);
        }
        var refOpenCloseTaxPeriodConfig =
                refOpenCloseTaxPeriodRepositoryPort.readByGlFullCodeYearTaxPeriodTransType(
                        command.getGlAccountPartFullCode(),
                        command.getCalendarYear(),
                        command.getTaxPeriodTypeId(),
                        command.getTransactionTypeId()
                );
        if (refOpenCloseTaxPeriodConfig.isPresent()) {
            return refOpenCloseTaxPeriodConfig.get();
        } else {
            return generate(command);
        }
    }

    public RefOpenCloseTaxPeriod generate(LoadGenOpenCloseTaxPeriodConfigCommand command) {
        Collection<RefOpenCloseTaxPeriodRecord> records = new LinkedList<>();

        int year = command.getCalendarYear();
        if (MONTHLY == command.getTaxPeriodTypeId()) {
            for (int monthIndex = 1; monthIndex <= 12; monthIndex++) {
                LocalDate startDate = LocalDate.of(year, monthIndex, 1).plusMonths(1);
                LocalDate endDate = command.getYears() != null
                        ? YearMonth.of(startDate.plusYears(command.getYears()).getYear(), startDate.plusYears(command.getYears()).getMonth()).atEndOfMonth()
                        : YearMonth.of(startDate.plusMonths(command.getMonth() - 1).getYear(), startDate.plusMonths(command.getMonth() - 1).getMonth()).atEndOfMonth();
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                monthIndex,
                                String.valueOf(Month.of(monthIndex)),
                                startDate,
                                endDate

                        ));
            }

        } else if (QUARTERLY == command.getTaxPeriodTypeId()) {
            LocalDate startOfQuarter = command.getMonth() != null ? LocalDate.of(year, 4, 1) :
                    LocalDate.of(year, 4, 1).plusYears(command.getYears());
            LocalDate endOfQuarter = startOfQuarter;
            for (int quarterIndex = 1; quarterIndex <= 4; quarterIndex++) {
                endOfQuarter = command.getMonth() != 0 ? startOfQuarter.plusMonths(command.getMonth() - 1) : startOfQuarter.plusYears(command.getYears());
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                20 + quarterIndex,
                                quarterIndex + quarter,
                                startOfQuarter,
                                YearMonth.of(endOfQuarter.getYear(), endOfQuarter.getMonth()).atEndOfMonth()
                        ));
                startOfQuarter = startOfQuarter.plusMonths(3);
            }

        } else if (FORTNIGHTLY == command.getTaxPeriodTypeId()) {

            LocalDate startOfFortnight;
            LocalDate endOfFortnight;
            for (int fortnightIndex = 2; fortnightIndex <= 25; fortnightIndex++) {
                int fortnightFirstDay;
                int fortnightMonth;
                if (1 == (fortnightIndex % 2)) {
                    fortnightMonth = (fortnightIndex / 2) + 1;
                    fortnightFirstDay = 1;
                } else {
                    fortnightMonth = fortnightIndex / 2;
                    fortnightFirstDay = 15;
                }

                year = fortnightMonth == 13 ? year + 1 : year;
                fortnightMonth = fortnightMonth == 13 ? 1 : fortnightMonth;
                startOfFortnight = LocalDate.of(year, fortnightMonth, fortnightFirstDay);
                endOfFortnight = startOfFortnight.plusMonths(command.getMonth()).minusDays(1);
                records.add(
                        RefOpenCloseTaxPeriodRecord.withoutId(
                                30 + (fortnightIndex - 1),
                                (fortnightIndex - 1) + fortnight,
                                startOfFortnight,
                                endOfFortnight
                        ));
            }
        }

        return RefOpenCloseTaxPeriod.withoutId(
                command.getGlAccountPartFullCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                records
        );
    }
}
