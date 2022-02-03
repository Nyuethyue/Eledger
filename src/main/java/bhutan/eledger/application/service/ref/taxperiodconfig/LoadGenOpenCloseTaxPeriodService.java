package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.domain.ref.taxperiod.OpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.LinkedList;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenOpenCloseTaxPeriodService implements LoadGenOpenCloseTaxPeriodUseCase {
    @Override
    public RefOpenCloseTaxPeriodConfig loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command) {
            return generate(command);
    }

    private static final long MONTHLY = 1;// 12 rows
    public RefOpenCloseTaxPeriodConfig generate(LoadGenOpenCloseTaxPeriodConfigCommand command) {
        System.out.println("I am here"+command.getTaxPeriodTypeId());
        Collection<OpenCloseTaxPeriodRecord> records = new LinkedList<>();
        if(MONTHLY == command.getTaxPeriodTypeId()) {
            int year = command.getCalendarYear();
            for(int monthIndex = 1;monthIndex <= 12;monthIndex++) {
                records.add(
                        OpenCloseTaxPeriodRecord.withoutId(
                                monthIndex,
                                String.valueOf(Month.of(monthIndex)),
                                LocalDate.of(year, monthIndex+1,1),// Start period
                                LocalDate.of(year+1, monthIndex+1,1)// Start period

                        ));
            }
        }

        return RefOpenCloseTaxPeriodConfig.withoutId(
                command.getTaxTypeCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                records
        );
    }
}
