package bhutan.eledger.application.port.in.eledger.transaction;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public interface CreateTransactionsUseCaseFactory {

    CreateTransactionsUseCase get(@NotNull String service);

}
