package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BalanceAccountPartTypeValidator {
    private final BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    void checkExistenceById(Integer id) {
        if (!balanceAccountPartTypeRepositoryPort.existById(id)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("balanceAccountPartPartId", "Part type by id: [" + id + "] doesn't exists.")
            );
        }
    }
}
