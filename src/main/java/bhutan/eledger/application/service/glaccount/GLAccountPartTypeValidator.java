package bhutan.eledger.application.service.glaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GLAccountPartTypeValidator {
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    void checkExistenceById(Integer id) {
        if (!glAccountPartTypeRepositoryPort.existById(id)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("glAccountPartPartId", "Part type by id: [" + id + "] doesn't exists.")
            );
        }
    }
}
