package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.CreateRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpdateOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertOpenCloseTaxPeriodService implements UpsertRefOpenCloseTaxPeriodUseCase {
    private final CreateRefOpenCloseTaxPeriodUseCase createRefOpenCloseTaxPeriodUseCase;
    private final UpdateOpenCloseTaxPeriodUseCase updateOpenCloseTaxPeriodUseCase;
    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;
    private final TransactionTypeRepositoryPort transactionTypeRepositoryPort;

    @Override
    public void upsert(UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        log.trace("Upserting open tax period with command: {}", command);

        validate(command);

        refOpenCloseTaxPeriodRepositoryPort.readByGlFullCodeYearTaxPeriodTransType(command.getGlAccountPartFullCode(),
                        command.getCalendarYear(), command.getTaxPeriodCode(), command.getTransactionTypeId())
                .ifPresentOrElse(
                        openCloseTaxPeriod ->
                                updateOpenCloseTaxPeriodUseCase.update(openCloseTaxPeriod, command),
                        () -> createRefOpenCloseTaxPeriodUseCase.create(command)
                );
    }

    void validate(UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        try {
            TaxPeriodType.of(command.getTaxPeriodCode());
        } catch (Exception e) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("taxPeriodCode",
                                    "Invalid tax period type code:" + command.getTaxPeriodCode()));

        }
        if (transactionTypeRepositoryPort.readById(command.getTransactionTypeId()).isEmpty()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Id", "Transaction type  with id: [" + command.getTransactionTypeId() + "] does not exists.")
            );

        }

        command.getRecords().forEach(r -> {
            if (r.getPeriodOpenDate().isAfter(r.getPeriodCloseDate())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("periodOpenDate", "Period open date should be before period close date.")
                );
            }

        });


    }
}