package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertTaxPeriodService implements UpsertTaxPeriodUseCase {

    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    void validate(UpsertTaxPeriodCommand command) {
        if (null != command.getValidFrom() && command.getValidFrom().getYear() < command.getCalendarYear()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Year", "Valid from year less than calendar year")
            );
        }
    }

    @Override
    public Long upsert(@Valid UpsertTaxPeriodCommand command) {
        log.trace("Upserting TaxPeriod with command: {}", command);
        validate(command);

        RefTaxPeriodConfig refTaxPeriodConfig = mapCommandToRefTaxPeriodConfig(command);

        log.trace("Persisting TaxPeriod: {}", refTaxPeriodConfig);

        Long id = refTaxPeriodRepositoryPort.create(refTaxPeriodConfig);

        log.debug("TaxPeriod with id: {} successfully created.", id);

        return id;
    }

    private RefTaxPeriodConfig mapCommandToRefTaxPeriodConfig(UpsertTaxPeriodCommand command) {
        return RefTaxPeriodConfig.withId(
                null,
                command.getTaxTypeCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getDueDateCountForReturnFiling(),
                command.getDueDateCountForPayment(),
                command.getValidFrom(),
                command.getValidTo(),
                command.getConsiderNonWorkingDays(),
                null
        );
    }
}
