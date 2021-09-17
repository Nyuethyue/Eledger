package bhutan.eledger.application.service.balanceaccount.history;

import bhutan.eledger.application.port.in.config.balanceaccount.history.GetBalanceAccountPartHistoryUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.history.BalanceAccountPartHistoryPort;
import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class GetBalanceAccountHistoryPartService implements GetBalanceAccountPartHistoryUseCase {

    private final BalanceAccountPartHistoryPort balanceAccountPartHistoryPort;

    @Override
    public HistoriesHolder<BalanceAccountPart> getHistoriesById(Long balanceAccountId) {

        return balanceAccountPartHistoryPort.findRevisionsById(balanceAccountId);

    }
}
