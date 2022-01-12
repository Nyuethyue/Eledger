package bhutan.eledger.application.port.in.ref.rrcocashcounter;

import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadRefRRCOCashCounterUseCase {

    Collection<RefRRCOCashCounter> readAll();

    RefRRCOCashCounter readById(@NotNull Long id);

    RefRRCOCashCounter readByCode(@NotNull String code);
}
