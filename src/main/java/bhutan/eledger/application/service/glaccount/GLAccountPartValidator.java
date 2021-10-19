package bhutan.eledger.application.service.glaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
class GLAccountPartValidator {

    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    void checkPartsExistence(Long parentId, Collection<String> glAccountPartCodes) {

        boolean isAnyGLAccountExistsForParent = glAccountPartRepositoryPort.existByParentIdAndCodeInList(
                parentId,
                glAccountPartCodes
        );

        if (isAnyGLAccountExistsForParent) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("glAccountPart", "One or more gl account part already exists. Codes: [" + glAccountPartCodes + "].")
            );
        }

    }
}
