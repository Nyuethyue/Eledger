package bhutan.eledger.application.port.out.ref.rrcocashcounters;

import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface RefRRCOCashCountersRepositoryPort {
    Long create(RefRRCOCashCounters refRRCOCashCounters);

    Collection<RefRRCOCashCounters> readAll();

    Optional<RefRRCOCashCounters> readById(Long id);

    void deleteAll();

    Optional<RefRRCOCashCounters> readByCode(String code, LocalDate currentDate);

    boolean isOpenRRCOCashCountersExists(RefRRCOCashCounters refRRCOCashCounters);
}
