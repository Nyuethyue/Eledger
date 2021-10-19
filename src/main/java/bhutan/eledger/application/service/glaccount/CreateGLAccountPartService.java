package bhutan.eledger.application.service.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import bhutan.eledger.domain.config.glaccount.GLAccountPartStatus;
import bhutan.eledger.domain.config.glaccount.ValidityPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateGLAccountPartService implements CreateGLAccountPartUseCase {
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;
    private final GLAccountPartTypeValidator glAccountPartTypeValidator;
    private final GLAccountPartValidator glAccountPartValidator;

    @Override
    public Collection<GLAccountPart> create(CreateGLAccountPartCommand command) {

        log.trace("Creating gl account part with command: {}", command);

        validate(command);

        var glAccountParts = mapCommandToGLAccountParts(command);

        log.trace("Persisting gl account parts: {}", glAccountParts);

        var persistedGLAccountParts = glAccountPartRepositoryPort.create(glAccountParts);

        log.debug("GL account part with codes: {} successfully created.", () -> persistedGLAccountParts.stream().map(GLAccountPart::getCode).collect(Collectors.toUnmodifiableList()));


        return persistedGLAccountParts;
    }

    private void validate(CreateGLAccountPartCommand command) {
        glAccountPartTypeValidator.checkExistenceById(command.getGlAccountPartTypeId());
        glAccountPartValidator.checkPartsExistence(
                command.getParentId(),
                command.getGlAccountParts()
                        .stream()
                        .map(GLAccountPartCommand::getCode)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    private Collection<GLAccountPart> mapCommandToGLAccountParts(CreateGLAccountPartCommand command) {

        LocalDateTime creationDateTime = LocalDateTime.now();

        return command.getGlAccountParts()
                .stream()
                .map(glAccountPartCommand ->
                        GLAccountPart.withoutId(
                                glAccountPartCommand.getCode(),
                                command.getParentId(),
                                GLAccountPartStatus.ACTIVE,
                                creationDateTime,
                                creationDateTime,
                                ValidityPeriod.withOnlyStartOfValidity(creationDateTime.toLocalDate().atStartOfDay()),
                                Multilingual.fromMap(glAccountPartCommand.getDescriptions()),
                                command.getGlAccountPartTypeId()
                        )
                )
                .collect(Collectors.toUnmodifiableList());
    }
}
