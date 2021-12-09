package bhutan.eledger.application.port.out.epayment.deposit;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;


import java.util.Collection;
import java.util.Optional;

public interface DepositRepositoryPort {

    Optional<Deposit> readById(Long id);

    Optional<Deposit> readByDepositNumber(String depositNumber);

    default Deposit requiredReadByDepositNumber(String depositNumber) {
        return readByDepositNumber(depositNumber)
                .orElseThrow(() ->
                        new RecordNotFoundException("Deposit by number: [" + depositNumber + "] not found.")
                );
    }

    default Deposit requiredReadById(Long id) {
        return readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Deposit by id: [" + id + "] not found.")
                );
    }

    Collection<Deposit> readAll();

    Deposit create(Deposit paymentAdvice);

    void deleteAll();

    void update(Deposit updatedDeposit);

    void updateStatus(Long depositId, DepositStatus status);
}
