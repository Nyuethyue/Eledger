package bhutan.eledger.application.service.eledger.glaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateGLAccountPartTypeService implements CreateGLAccountPartTypeUseCase {
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Override
    public Integer create(CreateGLAccountPartTypeCommand command) {
        log.trace("Creating gl account part type with command: {}", command);

        GLAccountPartType glAccountPartType = mapCommandToGLAccountPartType(command);

        validate(glAccountPartType);

        log.trace("Persisting gl account part type: {}", glAccountPartType);

        Integer id = glAccountPartTypeRepositoryPort.create(glAccountPartType);

        log.debug("GL account part type with id: {} successfully created.", id);

        return id;
    }

    private GLAccountPartType mapCommandToGLAccountPartType(CreateGLAccountPartTypeCommand command) {
        LocalDateTime creationDateTime = LocalDateTime.now();

        return GLAccountPartType.withoutId(
                command.getLevel(),
                creationDateTime,
                creationDateTime,
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(GLAccountPartType glAccountPartType) {
        if (glAccountPartTypeRepositoryPort.existByLevel(glAccountPartType.getLevel())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("GLAccountPartType", "Part type with level: [" + glAccountPartType.getLevel() + "] already exists.")
            );
        }
    }
}
