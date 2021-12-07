package bhutan.eledger.application.port.out.epayment.deposit;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.deposit.Deposit;


import java.util.Collection;
import java.util.Optional;

public interface DepositRepositoryPort {

    Optional<Deposit> readById(Long id);

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
}
