package bhutan.eledger.application.service.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountService implements CreateBalanceAccountUseCase {
    @Override
    public Long create(CreateBalanceAccountCommand command) {
        return null;
    }
}
