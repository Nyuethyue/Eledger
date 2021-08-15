package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartLevelUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartLevelRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountPartLevelService implements CreateBalanceAccountPartLevelUseCase {
    private final BalanceAccountPartLevelRepositoryPort balanceAccountPartLevelRepositoryPort;

    @Override
    public Long create(CreateBalanceAccountPartLevelCommand command) {
        log.trace("Creating balance account part level with command: {}", command);

        BalanceAccountPartLevel balanceAccountPartLevel = mapCommandToBalanceAccountPartLevel(command);

        log.trace("Persisting balance account part level: {}", balanceAccountPartLevel);


        Long id = balanceAccountPartLevelRepositoryPort.create(balanceAccountPartLevel);

        log.debug("Balance account part level with id: {} successfully created.", id);

        return id;
    }

    private BalanceAccountPartLevel mapCommandToBalanceAccountPartLevel(CreateBalanceAccountPartLevelCommand command) {
        return BalanceAccountPartLevel.withoutId(
                command.getLevel(),
                Multilingual.fromMap(command.getDescriptions())
        );
    }
}
