package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.glaccount.UpdateGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
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
class UpdateGLAccountService implements UpdateGLAccountUseCase {
    private final GLAccountRepositoryPort glAccountRepositoryPort;
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Override
    public void updateGLAccount(Long id, UpdateGLAccountCommand command) {
        log.trace("Updating gl account with id: {} by command: {}", id, command);

        GLAccount glAccount = glAccountRepositoryPort.readById(id).orElseThrow(() ->
                new RecordNotFoundException("GLAccount by id: [" + id + "] not found.")
        );

        log.trace("GL account to be updated: {}", glAccount);

        //todo apply validation logic

        GLAccount updatedGLAccount = GLAccount.withId(
                glAccount.getId(),
                glAccount.getCode(),
                glAccount.getCreationDateTime(),
                LocalDateTime.now(),
                glAccount.getDescription().merge(command.getDescriptions()),
                glAccount.getGlAccountLastPartId()
        );

        log.trace("Persisting updated gl account: {}", updatedGLAccount);

        glAccountRepositoryPort.update(updatedGLAccount);

        var glPartAccountExisted = glAccountPartRepositoryPort.readById(glAccount.getGlAccountLastPartId());

        if (glPartAccountExisted.get().getGlAccountPartLevelId()==7) {
            GLAccountPart updatedGLAccountPart = GLAccountPart.withId(
                    glPartAccountExisted.get().getId(),
                    glPartAccountExisted.get().getCode(),
                    glPartAccountExisted.get().getFullCode(),
                    glPartAccountExisted.get().getParentId(),
                    glPartAccountExisted.get().getCreationDateTime(),
                    LocalDateTime.now(),
                    glPartAccountExisted.get().getDescription().merge(command.getDescriptions()),
                    glPartAccountExisted.get().getGlAccountPartLevelId()
            );

            log.trace("Persisting updated gl part account: {}", updatedGLAccountPart);

            glAccountPartRepositoryPort.update(updatedGLAccountPart);
        }

    }
}
