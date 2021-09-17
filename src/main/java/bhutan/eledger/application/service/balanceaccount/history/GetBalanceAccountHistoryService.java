package bhutan.eledger.application.service.balanceaccount.history;

import bhutan.eledger.application.port.in.config.balanceaccount.history.GetBalanceAccountHistoryUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.history.BalanceAccountHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class GetBalanceAccountHistoryService implements GetBalanceAccountHistoryUseCase {

    private final BalanceAccountHistoryPort balanceAccountHistoryPort;

    @Override
    public HistoriesHolder<BalanceAccount> getHistoriesById(Long balanceAccountId) {

        return balanceAccountHistoryPort.findRevisionsById(balanceAccountId);

    }
}
