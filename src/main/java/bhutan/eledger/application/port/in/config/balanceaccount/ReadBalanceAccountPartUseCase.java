package bhutan.eledger.application.port.in.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadBalanceAccountPartUseCase {

    BalanceAccountPart readById(@NotNull Long id);

    Collection<BalanceAccountPart> readAllByParentId(@NotNull Long parentId);
}
