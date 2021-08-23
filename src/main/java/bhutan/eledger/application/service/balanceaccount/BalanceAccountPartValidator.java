package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
class BalanceAccountPartValidator {

    private final BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    void checkPartsExistence(Long parentId, Collection<String> balanceAccountPartCodes) {

        boolean isAnyBalanceAccountExistsForParent = balanceAccountPartRepositoryPort.existByParentIdAndCodeInList(
                parentId,
                balanceAccountPartCodes
        );

        if (isAnyBalanceAccountExistsForParent) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("balanceAccountPart", "One or more balance account part already exists. Codes: [" + balanceAccountPartCodes + "].")
            );
        }

    }
}
