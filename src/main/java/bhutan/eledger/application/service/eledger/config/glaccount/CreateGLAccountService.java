package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.Violation;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GetGlAccountPartFullCodeOnlyPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateGLAccountService implements CreateGLAccountUseCase {
    private final GLAccountRepositoryPort glAccountRepositoryPort;
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;
    private final GetGlAccountPartFullCodeOnlyPort getGlAccountPartParentFullCodePort;

    @Override
    public Long create(CreateGLAccountCommand command) {
        log.trace("Creating gl account with command: {}", command);

        var parentOfLastPart = resolveParentOfLastType(command);

        GLAccountPart lastPart = makeGLAccountPart(
                command.getGlAccountLastPart(),
                parentOfLastPart.getId(),
                glAccountPartTypeRepositoryPort.getIdOfNextPartType(parentOfLastPart.getGlAccountPartLevelId())
                        .orElseThrow(() -> new ViolationException(
                                        new ValidationError(
                                                Set.of(
                                                        new Violation("partType", "Part type for last part not found.")
                                                )
                                        )
                                )
                        )
        );

        log.trace("Persisting gl account last part: {}", lastPart);

        Long lastPartId = glAccountPartRepositoryPort.create(lastPart);

        log.debug("GL account last part with id: {} successfully created.", lastPartId);

        LocalDateTime creationDateTime = LocalDateTime.now();

        GLAccount glAccount = GLAccount.withoutId(
                lastPart.getFullCode(),
                creationDateTime,
                creationDateTime,
                Multilingual.fromMap(command.getDescriptions()),
                lastPartId
        );

        log.trace("Persisting gl account: {}", glAccount);

        Long id = glAccountRepositoryPort.create(glAccount);

        log.debug("GL account with id: {} successfully created.", id);

        return id;
    }

    private GLAccountPart makeGLAccountPart(GLAccountLastPartCommand command, Long parentId, Integer gleAccountPartTypeId) {

        LocalDateTime creationDateTime = LocalDateTime.now();

        String parentFullCode = getGlAccountPartParentFullCodePort.getGlAccountPartFullCodeOnly(parentId)
                .getFullCode();

        return GLAccountPart.withoutId(
                command.getCode(),
                parentFullCode + command.getCode(),
                parentId,
                creationDateTime,
                creationDateTime,
                Multilingual.fromMap(command.getDescriptions()),
                gleAccountPartTypeId
        );
    }

    private GLAccountPart resolveParentOfLastType(CreateGLAccountCommand command) {
        Long parentId = command.getGlAccountLastPart().getParentId();

        return glAccountPartRepositoryPort.readById(parentId)
                .orElseThrow(() ->
                        new ViolationException(
                                new ValidationError(
                                        Set.of(
                                                new Violation("glAccountLastPart.parentId", "Gl account part not found by id: " + parentId)
                                        )
                                )
                        )
                );
    }
}
