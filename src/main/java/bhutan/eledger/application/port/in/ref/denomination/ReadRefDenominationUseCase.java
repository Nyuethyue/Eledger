package bhutan.eledger.application.port.in.ref.denomination;

import bhutan.eledger.domain.ref.currency.RefCurrency;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadRefDenominationUseCase {
    Collection<RefCurrency> readAll();
}
