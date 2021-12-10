package bhutan.eledger.application.service.ref.bankbranch;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.bankbranch.ReadRefBankBranchUseCase;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefBankBranchService implements ReadRefBankBranchUseCase {
    private final RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @Override
    public Collection<RefBankBranch> readAll() {
        log.trace("Reading all bank's branch list.");

        return refBankBranchRepositoryPort.readAll();
    }

    @Override
    public RefBankBranch readById(Long id) {
        log.trace("Reading bank's branch by id: {}", id);

        return refBankBranchRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Bank's branch by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<RefBankBranch> readAllByBankId(Long bankId) {
        log.trace("Reading all branch information by bank id.");

        return refBankBranchRepositoryPort.readAllByBankId(bankId, LocalDate.now());
    }
}
