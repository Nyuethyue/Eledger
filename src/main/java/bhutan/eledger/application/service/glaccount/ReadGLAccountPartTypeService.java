package bhutan.eledger.application.service.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.config.glaccount.ReadGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.glaccount.GLAccountPartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
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
