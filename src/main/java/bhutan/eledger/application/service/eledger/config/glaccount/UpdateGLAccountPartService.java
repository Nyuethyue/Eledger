package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.glaccount.UpdateGLAccountPartUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpdateGLAccountPartService implements UpdateGLAccountPartUseCase {
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Override
    public void updateGLAccountPart(Long id, UpdateGLAccountPartUseCase.UpdateGLAccountPartCommand command) {
        log.trace("Updating gl account part with id: {} by command: {}", id, command);

        GLAccountPart glAccountPart = glAccountPartRepositoryPort.readById(id).orElseThrow(() ->
                new RecordNotFoundException("GLAccount part by id: [" + id + "] not found.")
        );

        log.trace("GL account part to be updated: {}", glAccountPart);

        //todo apply validation logic


        GLAccountPart updatedGLAccountPart = GLAccountPart.withId(
                glAccountPart.getId(),
                glAccountPart.getCode(),
                glAccountPart.getFullCode(),
                glAccountPart.getParentId(),
                glAccountPart.getCreationDateTime(),
                LocalDateTime.now(),
                glAccountPart.getDescription().merge(command.getDescriptions()),
                glAccountPart.getGlAccountPartLevelId()
        );

        log.trace("Persisting updated gl account Part: {}", updatedGLAccountPart);

        glAccountPartRepositoryPort.update(updatedGLAccountPart);
    }
}
