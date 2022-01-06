package bhutan.eledger.application.port.in.ref.rrcocashcounters;

import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadRefRRCOCashCountersUseCase {

    Collection<RefRRCOCashCounters> readAll();

    RefRRCOCashCounters readById(@NotNull Long id);

    RefRRCOCashCounters readByCode(@NotNull String code);
}
