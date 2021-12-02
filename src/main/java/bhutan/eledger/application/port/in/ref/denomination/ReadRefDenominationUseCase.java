package bhutan.eledger.application.port.in.ref.denomination;

import bhutan.eledger.domain.ref.denomination.RefDenomination;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;


@Validated
public interface ReadRefDenominationUseCase {
    Collection<RefDenomination> readAll();
}
