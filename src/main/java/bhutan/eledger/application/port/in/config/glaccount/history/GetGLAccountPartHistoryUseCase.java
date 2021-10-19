package bhutan.eledger.application.port.in.config.glaccount.history;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface GetGLAccountPartHistoryUseCase {

    HistoriesHolder<GLAccountPart> getHistoriesById(@NotNull Long glAccountId);
}
