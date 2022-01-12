package bhutan.eledger.application.service.ref.rrcocashcounter;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.rrcocashcounter.ReadRefRRCOCashCounterUseCase;
import bhutan.eledger.application.port.out.ref.rrcocashcounter.RefRRCOCashCounterRepositoryPort;
import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;
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
class ReadRefRRCOCashCounterService implements ReadRefRRCOCashCounterUseCase {

    private final RefRRCOCashCounterRepositoryPort refRRCOCashCounterRepositoryPort;

    @Override
    public Collection<RefRRCOCashCounter> readAll() {
        log.trace("Reading all RRCO Cash Counters list.");

        return refRRCOCashCounterRepositoryPort.readAll();
    }

    @Override
    public RefRRCOCashCounter readById(Long id) {
        log.trace("Reading RRCO Cash Counter by id: {}", id);

        return refRRCOCashCounterRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("RRCO Cash Counter by id: [" + id + "] not found.")
                );
    }

    @Override
    public RefRRCOCashCounter readByCode(String code) {
        log.trace("Reading RRCO Cash counters by Code : {}", code);

        return refRRCOCashCounterRepositoryPort.readByCode(code, LocalDate.now())
                .orElseThrow(() ->
                        new RecordNotFoundException("RRCO Cash Counter by code: [" + code + "] not found.")
                );
    }
}
