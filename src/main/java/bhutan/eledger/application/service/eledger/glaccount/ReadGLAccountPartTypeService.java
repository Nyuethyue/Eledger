package bhutan.eledger.application.service.eledger.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.glaccount.ReadGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadGLAccountPartTypeService implements ReadGLAccountPartTypeUseCase {
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Override
    public GLAccountPartType readById(Integer id) {
        log.trace("Reading gl account part type by id: {}", id);

        return glAccountPartTypeRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("GLAccountPartType by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<GLAccountPartType> readAll() {
        log.trace("Reading all gl account part types.");

        return glAccountPartTypeRepositoryPort.readAll();
    }
}
