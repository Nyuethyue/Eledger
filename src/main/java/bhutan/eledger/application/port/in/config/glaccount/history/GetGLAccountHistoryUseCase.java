package bhutan.eledger.application.port.in.config.glaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface GetGLAccountHistoryUseCase {

    HistoriesHolder<GLAccount> getHistoriesById(@NotNull Long glAccountId);
}
