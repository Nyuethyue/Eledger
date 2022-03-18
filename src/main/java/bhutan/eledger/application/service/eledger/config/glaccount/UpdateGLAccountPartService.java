package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.glaccount.UpdateGLAccountPartUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
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
    private final GLAccountRepositoryPort glAccountRepositoryPort;
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Override
    public void updateGLAccountPart(Long id, UpdateGLAccountPartUseCase.UpdateGLAccountPartCommand command) {
        log.trace("Updating gl account part with id: {} by command: {}", id, command);

        GLAccountPart glAccountPart = glAccountPartRepositoryPort.readById(id).orElseThrow(() ->
                new RecordNotFoundException("GLAccount part by id: [" + id + "] not found.")
        );

        log.trace("GL account part to be updated: {}", glAccountPart);

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

        var glAccountPartType = glAccountPartTypeRepositoryPort.readById(glAccountPart.getGlAccountPartLevelId())
                .orElseThrow(() -> new IllegalStateException("GLAccountPartType by id: " + glAccountPart.getGlAccountPartLevelId() + " not exists."));

        if (glAccountPartType.getLevel() == 7) {
            //todo need to improve  code
            var glAccountExisted = glAccountRepositoryPort.readByCode(glAccountPart.getFullCode())
                    .orElseThrow(() -> new IllegalStateException("GLAccount by code: " + glAccountPart.getFullCode() + " not exists."));

            GLAccount updatedGLAccount = GLAccount.withId(
                    glAccountExisted.getId(),
                    glAccountExisted.getCode(),
                    glAccountExisted.getCreationDateTime(),
                    LocalDateTime.now(),
                    glAccountExisted.getDescription().merge(command.getDescriptions()),
                    glAccountExisted.getGlAccountLastPartId()
            );

            log.trace("Persisting updated gl account: {}", updatedGLAccount);

            glAccountRepositoryPort.update(updatedGLAccount);
        }
    }

}
