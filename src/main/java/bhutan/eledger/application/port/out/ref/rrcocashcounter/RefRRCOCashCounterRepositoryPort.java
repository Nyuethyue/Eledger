package bhutan.eledger.application.port.out.ref.rrcocashcounter;

import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface RefRRCOCashCounterRepositoryPort {
    Long create(RefRRCOCashCounter refRRCOCashCounter);

    Collection<RefRRCOCashCounter> readAll();

    Optional<RefRRCOCashCounter> readById(Long id);

    void deleteAll();

    Optional<RefRRCOCashCounter> readByCode(String code, LocalDate currentDate);

    boolean isOpenRRCOCashCountersExists(RefRRCOCashCounter refRRCOCashCounter);
}
