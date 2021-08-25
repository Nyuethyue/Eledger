package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.config.balanceaccount.ReadBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
class ReadBalanceAccountPartTypeService implements ReadBalanceAccountPartTypeUseCase {
    private final BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Override
    public BalanceAccountPartType readById(Integer id) {
        log.trace("Reading balance account part type by id: {}", id);

        return balanceAccountPartTypeRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("BalanceAccountPartType by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<BalanceAccountPartType> readAll() {
        log.trace("Reading all balance account part types.");

        return balanceAccountPartTypeRepositoryPort.readAll();
    }
}
