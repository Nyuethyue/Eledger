package bhutan.eledger.application.service.ref.denomination;

import bhutan.eledger.application.port.in.ref.currency.ReadRefCurrencyUseCase;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefDenominationService implements ReadRefCurrencyUseCase {
    private final RefCurrencyRepositoryPort refCurrencyRepositoryPort;

    @Override
    public Collection<RefCurrency> readAll() {
        log.trace("Reading all currency.");

        return refCurrencyRepositoryPort.readAll();
    }
}
