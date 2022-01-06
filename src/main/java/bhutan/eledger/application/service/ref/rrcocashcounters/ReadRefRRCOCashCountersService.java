package bhutan.eledger.application.service.ref.rrcocashcounters;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.rrcocashcounters.ReadRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.out.ref.rrcocashcounters.RefRRCOCashCountersRepositoryPort;
import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefRRCOCashCountersService implements ReadRefRRCOCashCountersUseCase {

    private final RefRRCOCashCountersRepositoryPort refRRCOCashCountersRepositoryPort;

    @Override
    public Collection<RefRRCOCashCounters> readAll() {
        log.trace("Reading all RRCO Cash Counters list.");

        return refRRCOCashCountersRepositoryPort.readAll();
    }

    @Override
    public RefRRCOCashCounters readById(Long id) {
        log.trace("Reading RRCO Cash Counter by id: {}", id);

        return refRRCOCashCountersRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("RRCO Cash Counter by id: [" + id + "] not found.")
                );
    }

    @Override
    public RefRRCOCashCounters readByCode(String code) {
        log.trace("Reading RRCO Cash counters by Code : {}", code);

        return refRRCOCashCountersRepositoryPort.readByCode(code, LocalDate.now())
                .orElseThrow(() ->
                        new RecordNotFoundException("RRCO Cash Counter by code: [" + code + "] not found.")
                );
    }
}
