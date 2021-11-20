package bhutan.eledger.application.service.ref.currency;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefCurrencyService implements CreateRefCurrencyUseCase {

    private final RefCurrencyRepositoryPort refCurrencyRepositoryPort;

    @Override
    public Long create(CreateRefCurrencyUseCase.CreateCurrencyCommand command) {
        log.trace("Creating currency with command: {}", command);

        RefCurrency refCurrency = mapCommandToRefCurrency(command);

        log.trace("Persisting currency: {}", refCurrency);

        Long id = refCurrencyRepositoryPort.create(refCurrency);

        log.debug("Branch with id: {} successfully created.", id);

        return id;
    }

    private RefCurrency mapCommandToRefCurrency(CreateRefCurrencyUseCase.CreateCurrencyCommand command) {
        return RefCurrency.withoutId(
                command.getCode(),
                command.getSymbol(),
                Multilingual.fromMap(command.getDescription())
        );
    }
}
