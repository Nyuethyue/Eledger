package bhutan.eledger.application.port.in.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadBalanceAccountPartTypeUseCase {

    BalanceAccountPartType readById(@NotNull Integer id);

    Collection<BalanceAccountPartType> readAll();
}
