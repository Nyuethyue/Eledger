package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GetGlAccountPartFullCodeOnlyPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
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
    private final GetGlAccountPartFullCodeOnlyPort getGlAccountPartParentFullCodePort;

    @Override
    public Collection<GLAccountPart> create(CreateGLAccountPartCommand command) {

        log.trace("Creating gl account part with command: {}", command);

        validate(command);

        var glAccountParts = makeGLAccountParts(command);

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

    private Collection<GLAccountPart> makeGLAccountParts(CreateGLAccountPartCommand command) {

        LocalDateTime creationDateTime = LocalDateTime.now();

        String parentFullCode = getGlAccountPartParentFullCodePort.getGlAccountPartFullCodeOnly(command.getParentId())
                .getFullCode();

        return command.getGlAccountParts()
                .stream()
                .map(glAccountPartCommand -> {
                    String code = glAccountPartCommand.getCode();

                    String fullCode = parentFullCode.isEmpty() ? code : parentFullCode + code;

                    return GLAccountPart.withoutId(
                            glAccountPartCommand.getCode(),
                            fullCode,
                            command.getParentId(),
                            creationDateTime,
                            creationDateTime,
                            Multilingual.fromMap(glAccountPartCommand.getDescriptions()),
                            command.getGlAccountPartTypeId()
                    );
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
