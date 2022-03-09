package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.eledger.config.glaccount.ReadGLAccountPartUseCase;
import bhutan.eledger.application.port.in.ref.nonworkingdays.ReadRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadTaxPeriodSegmentsUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodConfigRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenTaxPeriodConfigService implements LoadGenTaxPeriodConfigUseCase {
    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    private final ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;
    private final LoadTaxPeriodSegmentsUseCase loadTaxPeriodSegmentsUseCase;
    private final ReadRefNonWorkingDaysUseCase readRefNonWorkingDaysUseCase;
    private final ReadGLAccountPartUseCase readGLAccountPartUseCase;

    @Override
    public RefTaxPeriodConfig loadGen(@Valid LoadGenTaxPeriodConfigCommand command) {
        log.trace("Loading tax period record with command: {}", command);
        var refTaxPeriodConfig =
                refTaxPeriodRepositoryPort.searchAll(
                        command.getTaxTypeCode(),
                        command.getCalendarYear(),
                        command.getTaxPeriodCode(),
                        command.getTransactionTypeId()
                );

        if (refTaxPeriodConfig.size() > 0) {
            return refTaxPeriodConfig.iterator().next();
        } else {
            return generate(command);
        }
    }

    private static final int TAX_TYPE_GL_ACCOUNT_PART_TYPE_ID = 5;

    private Map<String, String> loadTaxTypeFullCodeMap() {
        Map<String, String> result = new HashMap<>();
        Collection<GLAccountPart> taxTypes = readGLAccountPartUseCase.readAllByPartTypeId(TAX_TYPE_GL_ACCOUNT_PART_TYPE_ID);
        taxTypes.stream().forEach(tt -> {
            var tv = tt.getDescription().translationValue("en");
            if(tv.isPresent()) {
                result.put(tt.getFullCode(), tv.get());
            }
        });
        return result;
    }

    private static String taxTypeCodeDisplayValue(String taxTypeCode, Map<String, String> taxTypeCodeMap) {
        if(taxTypeCodeMap.containsKey(taxTypeCode)) {
            return taxTypeCodeMap.get(taxTypeCode);
        } else {
            return taxTypeCode;
        }
    }

    public RefTaxPeriodConfig generate(LoadGenTaxPeriodConfigCommand command) {
        var taxPeriodType = readTaxPeriodTypesUseCase.readByCode(command.getTaxPeriodCode());

        Collection<RefTaxPeriodSegment> segments =
                loadTaxPeriodSegmentsUseCase.findByTaxPeriodId(taxPeriodType.get().getId());
        Map<String, String> taxTypeFullCodeMap = loadTaxTypeFullCodeMap();
        String taxTypeCodeDisplayValue = taxTypeCodeDisplayValue(command.getTaxTypeCode(), taxTypeFullCodeMap);
        int year = command.getCalendarYear();
        Set<Integer> nonWorkingDays = loadNonWorkingDays(year);
        Collection<TaxPeriodConfigRecord> records = new LinkedList<>();
        boolean consider = command.getConsiderNonWorkingDays();
        if (TaxPeriodType.MONTHLY.getValue().equals(command.getTaxPeriodCode())) {
            int monthIndex = 0;
            for (RefTaxPeriodSegment segment : segments) {
                monthIndex++;
                LocalDate endOfMonth = YearMonth.of(year, monthIndex).atEndOfMonth();
                records.add(
                        TaxPeriodConfigRecord.withoutId(
                                segment.getId(),
                                segment.getCode(),
                                segment.getDescription(),
                                LocalDate.of(year, monthIndex, 1),// Start period
                                endOfMonth,// End period
                                addDays(endOfMonth, command.getDueDateCountForReturnFiling(), consider, nonWorkingDays),
                                addDays( endOfMonth, command.getDueDateCountForPayment(), consider, nonWorkingDays),
                                addDays( endOfMonth, command.getDueDateCountForReturnFiling() + 1, consider, nonWorkingDays),
                                addDays( endOfMonth, command.getDueDateCountForPayment() + 1, consider, nonWorkingDays),
                                command.getValidFrom(),
                                taxTypeCodeDisplayValue
                        ));
            }
        } else if (TaxPeriodType.QUARTERLY.getValue().equals(command.getTaxPeriodCode())) {
            int quarterIndex = 0;
            for (RefTaxPeriodSegment segment : segments) {
                quarterIndex++;
                LocalDate endOfQuarter = YearMonth.of(year, 3 * quarterIndex).atEndOfMonth();
                records.add(
                        TaxPeriodConfigRecord.withoutId(
                                segment.getId(),
                                segment.getCode(),
                                segment.getDescription(),
                                LocalDate.of(year, (3 * quarterIndex) - 2, 1),// Start period
                                endOfQuarter,// End period
                                addDays(endOfQuarter, command.getDueDateCountForReturnFiling(), consider, nonWorkingDays),
                                addDays(endOfQuarter, command.getDueDateCountForPayment(), consider, nonWorkingDays),
                                addDays(endOfQuarter, command.getDueDateCountForReturnFiling() + 1, consider, nonWorkingDays),
                                addDays(endOfQuarter, command.getDueDateCountForPayment() + 1, consider, nonWorkingDays),
                                command.getValidFrom(),
                                taxTypeCodeDisplayValue
                        ));
            }
        } else if (TaxPeriodType.FORTNIGHTLY.getValue().equals(command.getTaxPeriodCode())) {
            int fortnightIndex = 0;
            LocalDate endOfFortnight;
            for (RefTaxPeriodSegment segment : segments) {
                fortnightIndex++;
                int fortnightFirstDay;
                int fortnightMonth;
                if (1 == (fortnightIndex % 2)) {
                    fortnightMonth = (fortnightIndex / 2) + 1;
                    fortnightFirstDay = 1;
                    endOfFortnight = LocalDate.of(year, fortnightMonth, 15);
                } else {
                    fortnightMonth = fortnightIndex / 2;
                    fortnightFirstDay = 16;
                    endOfFortnight = YearMonth.of(year, fortnightMonth).atEndOfMonth();
                }
                records.add(
                        TaxPeriodConfigRecord.withoutId(
                                segment.getId(),
                                segment.getCode(),
                                segment.getDescription(),
                                LocalDate.of(year, fortnightMonth, fortnightFirstDay),// Start period
                                endOfFortnight,// End period
                                addDays(endOfFortnight, command.getDueDateCountForReturnFiling(), consider, nonWorkingDays),
                                addDays(endOfFortnight, command.getDueDateCountForPayment(), consider, nonWorkingDays),
                                addDays(endOfFortnight, command.getDueDateCountForReturnFiling() + 1, consider, nonWorkingDays),
                                addDays(endOfFortnight, command.getDueDateCountForPayment() + 1, consider, nonWorkingDays),
                                command.getValidFrom(),
                                taxTypeCodeDisplayValue
                        ));
            }
        }  else if (TaxPeriodType.HALFYEARLY.getValue().equals(command.getTaxPeriodCode())) {
            int halfIndex = 0;
            for (RefTaxPeriodSegment segment : segments) {
                halfIndex++;
                LocalDate endOfHalf = YearMonth.of(year, 6 * halfIndex).atEndOfMonth();
                records.add(
                        TaxPeriodConfigRecord.withoutId(
                                segment.getId(),
                                segment.getCode(),
                                segment.getDescription(),
                                LocalDate.of(year, (6 * halfIndex) - 5, 1),// Start period
                                endOfHalf,// End period
                                addDays(endOfHalf, command.getDueDateCountForReturnFiling(), consider, nonWorkingDays),
                                addDays(endOfHalf, command.getDueDateCountForPayment(), consider, nonWorkingDays),
                                addDays(endOfHalf, command.getDueDateCountForReturnFiling() + 1, consider, nonWorkingDays),
                                addDays(endOfHalf, command.getDueDateCountForPayment() + 1, consider, nonWorkingDays),
                                command.getValidFrom(),
                                taxTypeCodeDisplayValue
                        ));
            }
        }  else if (TaxPeriodType.YEARLY.getValue().equals(command.getTaxPeriodCode())) {
            RefTaxPeriodSegment segment = segments.iterator().next();
                LocalDate endOfYear = YearMonth.of(year, 12).atEndOfMonth();
                records.add(
                        TaxPeriodConfigRecord.withoutId(
                                segment.getId(),
                                segment.getCode(),
                                segment.getDescription(),
                                LocalDate.of(year, 1, 1),// Start period
                                endOfYear,// End period
                                addDays(endOfYear, command.getDueDateCountForReturnFiling(), consider, nonWorkingDays),
                                addDays(endOfYear, command.getDueDateCountForPayment(), consider, nonWorkingDays),
                                addDays(endOfYear, command.getDueDateCountForReturnFiling() + 1, consider, nonWorkingDays),
                                addDays(endOfYear, command.getDueDateCountForPayment() + 1, consider, nonWorkingDays),
                                command.getValidFrom(),
                                taxTypeCodeDisplayValue
                        ));
        }

        return RefTaxPeriodConfig.withoutId(
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

    private Set<Integer> loadNonWorkingDays(int year) {
        Set<Integer> result = new HashSet<>();
        Collection<RefNonWorkingDays> nwDays = readRefNonWorkingDaysUseCase.readAll();
        nwDays.stream().forEach(d -> {
            int sd = d.getStartDay().atYear(year).getDayOfYear();
            int ed = d.getEndDay().atYear(year).getDayOfYear();
            for (int dayOfYear = sd; dayOfYear < ed; dayOfYear++) {
                if(containsDay(year, dayOfYear, d.getValidityPeriod().getStart(), d.getValidityPeriod().getEnd()))
                result.add(dayOfYear);
            }
        });
        return result;
    }

    private boolean containsDay(int year, int dayOfYear, LocalDate start, LocalDate end) {
        LocalDate dateToCheck = Year.of(year).atDay(dayOfYear);
        if(null == end) {
            return !dateToCheck.isBefore(start);
        } else {
            return !dateToCheck.isBefore(start) && !dateToCheck.isAfter(end);
        }
    }

    private LocalDate addDays(LocalDate dateFrom, int dayCount, boolean considerNonWorkingDays, Set<Integer> nonWorkingDays) {
        LocalDate dateTo = dateFrom.plusDays(dayCount);
        if(!considerNonWorkingDays) {
            return dateTo;
        }
        int startDay = dateFrom.getDayOfYear();
        int endDay = dateTo.getDayOfYear();
        int numberOfNonWorkingDays;
        if (startDay <= endDay) {
            numberOfNonWorkingDays = countNWDays(dateFrom, nonWorkingDays, startDay, endDay);
        } else {
            int middleDay = dateFrom.lengthOfYear();
            numberOfNonWorkingDays = countNWDays(dateFrom, nonWorkingDays, startDay, middleDay)
                                     + countNWDays(dateTo, nonWorkingDays, 1, endDay);
        }
        return dateFrom.plusDays(dayCount + numberOfNonWorkingDays);
    }

    private int countNWDays(LocalDate dateFrom, Set<Integer> nonWorkingDays, int startDay, int endDay) {
        int numberOfNonWorkingDays = 0;
        for (int day = startDay; day < endDay; day++) {
            if (nonWorkingDays.contains(day)) {
                numberOfNonWorkingDays++;
            } else {
                LocalDate dayDate = dateFrom.withDayOfYear(day);
                DayOfWeek dayOfWeek = dayDate.getDayOfWeek();
                if(DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
                    numberOfNonWorkingDays++;
                }
            }
        }
        return numberOfNonWorkingDays;
    }
}
