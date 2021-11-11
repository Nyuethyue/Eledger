package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.glaccount.UpdateGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountStatus;
import bhutan.eledger.domain.eledger.config.glaccount.ValidityPeriod;
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

    @Override
    public void updateGLAccount(Long id, UpdateGLAccountCommand command) {
        log.trace("Updating gl account with id: {} by command: {}", id, command);

        GLAccount glAccount = glAccountRepositoryPort.readById(id).orElseThrow(() ->
                new RecordNotFoundException("GLAccount by id: [" + id + "] not found.")
        );

        log.trace("GL account to be updated: {}", glAccount);

        //todo apply validation logic

        ValidityPeriod validityPeriod = resolveValidityPeriod(glAccount, command);

        GLAccount updatedGLAccount = GLAccount.withId(
                glAccount.getId(),
                glAccount.getCode(),
                command.getGlAccountStatus(),
                glAccount.getCreationDateTime(),
                LocalDateTime.now(),
                validityPeriod,
                glAccount.getDescription().merge(command.getDescriptions()),
                glAccount.getGlAccountLastPartId()
        );

        log.trace("Persisting updated gl account: {}", updatedGLAccount);

        glAccountRepositoryPort.update(updatedGLAccount);
    }

    private ValidityPeriod resolveValidityPeriod(GLAccount glAccount, UpdateGLAccountCommand command) {

        ValidityPeriod result;

        if (glAccount.getStatus() == command.getGlAccountStatus()) {
            result = glAccount.getValidityPeriod();
        } else if (command.getGlAccountStatus() == GLAccountStatus.INACTIVE) {
            result = ValidityPeriod.of(glAccount.getValidityPeriod().getStart(), command.getActualDate());
        } else {
            result = ValidityPeriod.withOnlyStartOfValidity(command.getActualDate());
        }

        return result;
    }
}
