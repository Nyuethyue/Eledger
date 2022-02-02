package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

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

    public RefTaxPeriodConfig generate(LoadGenTaxPeriodConfigCommand command) {
        RefTaxPeriodConfig result = null;
//        return RefTaxPeriodConfig.withoutId(
//        command.getId(),
//        taxTypeId(),
//        calendarYear(),
//        taxPeriodTypeId,
//        transactionTypeId,
//        dueDateCountForReturnFiling,
//        dueDateCountForPayment,
//        validFrom,
//        validTo,
//        considerNonWorkingDays,
//        records,
//        );
        return result;
    }
}
