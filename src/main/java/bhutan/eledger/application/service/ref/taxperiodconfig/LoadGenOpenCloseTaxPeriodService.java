package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiod.OpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.DateUtil;
import org.hibernate.metamodel.model.convert.internal.OrdinalEnumValueConverter;
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

    @Override
    public RefOpenCloseTaxPeriodConfig loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command) {
        var refOpenCloseTaxPeriodConfig =
                refOpenCloseTaxPeriodRepositoryPort.readBy(
                        command.getGlAccountFullCode(),
                        command.getCalendarYear(),
                        command.getTaxPeriodTypeId(),
                        command.getTransactionTypeId()
                );
        if(refOpenCloseTaxPeriodConfig.isPresent()) {
            return refOpenCloseTaxPeriodConfig.get();
        } else {
            return generate(command);
        }
    }

    private static final long MONTHLY = 1;// 12 rows
    private static final long QUARTERLY = 2; // 4 rows
    private static final long FORTNIGHTLY = 3; // 24 rows

    public RefOpenCloseTaxPeriodConfig generate(LoadGenOpenCloseTaxPeriodConfigCommand command) {
        Collection<OpenCloseTaxPeriodRecord> records = new LinkedList<>();
        int year = command.getCalendarYear();
        if (MONTHLY == command.getTaxPeriodTypeId()) {
            for (int monthIndex = 1; monthIndex <= 12; monthIndex++) {
                LocalDate startDate = LocalDate.of(year, monthIndex, 1).plusMonths(1);
                LocalDate endDate = command.getYears() != 0
                        ? YearMonth.of(startDate.plusYears(command.getYears()).getYear(),startDate.plusYears(command.getYears()).getMonth()).atEndOfMonth()
                        : YearMonth.of(startDate.plusMonths(command.getMonth()-1).getYear(),startDate.plusMonths(command.getMonth()-1).getMonth()).atEndOfMonth();
                records.add(
                        OpenCloseTaxPeriodRecord.withoutId(
                                monthIndex,
                                String.valueOf(Month.of(monthIndex)),
                                startDate,
                                endDate// Start period

                        ));
            }
        } else if (QUARTERLY == command.getTaxPeriodTypeId()) {
            LocalDate startOfQuarter =command.getMonth()!=0 ?LocalDate.of(year, 4,1):
                    LocalDate.of(year, 4,1).plusYears(command.getYears());
            LocalDate endOfQuarter =startOfQuarter;
            for (int quarterIndex = 1; quarterIndex <= 4; quarterIndex++) {
                endOfQuarter = command.getMonth()!=0?startOfQuarter.plusMonths(command.getMonth()-1):startOfQuarter.plusYears(command.getYears());
                records.add(
                        OpenCloseTaxPeriodRecord.withoutId(
                                20 + quarterIndex,
                                quarterIndex + " quarter",
                                startOfQuarter,
                                YearMonth.of(endOfQuarter.getYear(),endOfQuarter.getMonth()).atEndOfMonth()
                        ));
                startOfQuarter = startOfQuarter.plusMonths(3);
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
                        OpenCloseTaxPeriodRecord.withoutId(
                                30 + fortnightIndex,
                                fortnightIndex + "fortnight",
                                LocalDate.of(year, fortnightMonth, fortnightFirstDay),// Start period
                                endOfFortnight
                        ));
            }
        }
        System.out.println("I am here" + command);

        return RefOpenCloseTaxPeriodConfig.withoutId(
                command.getGlAccountFullCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                records
        );
    }
}
