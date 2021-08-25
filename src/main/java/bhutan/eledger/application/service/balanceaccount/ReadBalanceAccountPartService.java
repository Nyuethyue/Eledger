package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.config.balanceaccount.ReadBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
class ReadBalanceAccountPartService implements ReadBalanceAccountPartUseCase {
    private final BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Override
    public BalanceAccountPart readById(Long id) {
        log.trace("Reading balance account part by id: {}", id);

        return balanceAccountPartRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("BalanceAccountPartType by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<BalanceAccountPart> readAllByParentId(Long parentId) {
        log.trace("Reading all balance account parts by parent id: {}", parentId);

        return balanceAccountPartRepositoryPort.readAllByParentId(parentId);
    }
}
