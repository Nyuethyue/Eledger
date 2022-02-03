package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.LinkedList;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenTaxPeriodConfigService implements LoadGenTaxPeriodConfigUseCase {
    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    @Override
    public RefTaxPeriodConfig loadGen(@Valid LoadGenTaxPeriodConfigCommand command) {
        var refTaxPeriodConfig =
                refTaxPeriodRepositoryPort.readBy(
                        command.getTaxTypeCode(),
                        command.getCalendarYear(),
                        command.getTaxPeriodTypeId(),
                        command.getTransactionTypeId()
                );
        if(refTaxPeriodConfig.isPresent()) {
            return refTaxPeriodConfig.get();
        } else {
            return generate(command);
        }
    }

    private static final long MONTHLY = 1;// 12 rows
    private static final long QUARTERLY = 2; // 4 rows
    private static final long FORTNIGHTLY = 3; // 24 rows

    public RefTaxPeriodConfig generate(LoadGenTaxPeriodConfigCommand command) {
        Collection<TaxPeriodRecord> records = new LinkedList<>();
        if(MONTHLY == command.getTaxPeriodTypeId()) {
            int year = command.getCalendarYear();
            for(int monthIndex = 1;monthIndex <= 12;monthIndex++) {
                LocalDate endOfMonth = YearMonth.of(year, monthIndex).atEndOfMonth();
                records.add(
                    TaxPeriodRecord.withoutId(
                            monthIndex,
                            LocalDate.of(year, monthIndex,1),// Start period
                            endOfMonth,// End period
                            endOfMonth.plusDays(command.getDueDateCountForReturnFiling()),
                            endOfMonth.plusDays(command.getDueDateCountForPayment()),
                            endOfMonth.plusDays(command.getDueDateCountForPayment() + 1),
                            endOfMonth.plusDays(command.getDueDateCountForPayment() + 1),
                            command.getValidFrom(),
                            command.getTaxTypeCode()
                    ));
            }
        } else
        if(QUARTERLY == command.getTaxPeriodTypeId()) {
            int year = command.getCalendarYear();
            for(int quarterIndex = 1;quarterIndex <= 4;quarterIndex++) {
                LocalDate endOfQuarter = YearMonth.of(year, 3 * quarterIndex).atEndOfMonth();
                records.add(
                        TaxPeriodRecord.withoutId(
                                quarterIndex,
                                LocalDate.of(year, (3 * quarterIndex) - 2,1),// Start period
                                endOfQuarter,// End period
                                endOfQuarter.plusDays(command.getDueDateCountForReturnFiling()),
                                endOfQuarter.plusDays(command.getDueDateCountForPayment()),
                                endOfQuarter.plusDays(command.getDueDateCountForPayment() + 1),
                                endOfQuarter.plusDays(command.getDueDateCountForPayment() + 1),
                                command.getValidFrom(),
                                command.getTaxTypeCode()
                        ));
            }
        } else
        if(FORTNIGHTLY == command.getTaxPeriodTypeId()) {
            int year = command.getCalendarYear();
            LocalDate endOfFortnight;
            for(int fortnightIndex = 1;fortnightIndex <= 24;fortnightIndex++) {
                int fortnightFirstDay;
                int fortnightMonth;
                if(1 == (fortnightIndex % 2)) {
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
                                endOfFortnight.plusDays(command.getDueDateCountForReturnFiling()),
                                endOfFortnight.plusDays(command.getDueDateCountForPayment()),
                                endOfFortnight.plusDays(command.getDueDateCountForPayment() + 1),
                                endOfFortnight.plusDays(command.getDueDateCountForPayment() + 1),
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
}
