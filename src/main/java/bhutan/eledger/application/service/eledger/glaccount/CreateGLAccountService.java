package bhutan.eledger.application.service.eledger.glaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.Violation;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateGLAccountService implements CreateGLAccountUseCase {
    private final GLAccountRepositoryPort glAccountRepositoryPort;
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Override
    public Long create(CreateGLAccountCommand command) {
        log.trace("Creating gl account with command: {}", command);

        List<GLAccountPart> glAccountPart =
                glAccountPartRepositoryPort.readAllByIdInSortedByLevel(command.getGlAccountPartIds());
        //todo validate ids count and result count matching

        String bankAccountCodeWithoutLastPart = glAccountPart
                .stream()
                .map(GLAccountPart::getCode)
                .collect(Collectors.joining());

        var parentOfLastPart = glAccountPart.get(glAccountPart.size() - 1);

        GLAccountPart lastPart = mapCommandToGLAccount(
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
                bankAccountCodeWithoutLastPart + lastPart.getCode(),
                GLAccountStatus.ACTIVE,
                creationDateTime,
                creationDateTime,
                ValidityPeriod.withOnlyStartOfValidity(creationDateTime.toLocalDate().atStartOfDay()),
                Multilingual.fromMap(command.getDescriptions()),
                lastPartId
        );

        log.trace("Persisting gl account: {}", glAccount);

        Long id = glAccountRepositoryPort.create(glAccount);

        log.debug("GL account with id: {} successfully created.", id);

        return id;
    }

    private GLAccountPart mapCommandToGLAccount(GLAccountLastPartCommand command, Long parentId, Integer gleAccountPartTypeId) {

        LocalDateTime creationDateTime = LocalDateTime.now();

        return GLAccountPart.withoutId(
                command.getCode(),
                parentId,
                GLAccountPartStatus.ACTIVE,
                creationDateTime,
                creationDateTime,
                ValidityPeriod.withOnlyStartOfValidity(creationDateTime.toLocalDate().atStartOfDay()),
                Multilingual.fromMap(command.getDescriptions()),
                gleAccountPartTypeId
        );
    }
}
