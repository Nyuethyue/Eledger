package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.CreateRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpdateOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
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

    @Override
    public void upsert(UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        refOpenCloseTaxPeriodRepositoryPort.readByGlFullCodeYearTaxPeriodTransType(command.getGlAccountFullCode(),
                        command.getCalendarYear(), command.getTaxPeriodTypeId(), command.getTransactionTypeId())
                .ifPresentOrElse(
                        openCloseTaxPeriod ->
                                updateOpenCloseTaxPeriodUseCase.update(openCloseTaxPeriod, command),
                        () -> createRefOpenCloseTaxPeriodUseCase.create(command)
                );
    }
}