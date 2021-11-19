package bhutan.eledger.application.service.ref.bankbranch;

import bhutan.eledger.application.port.in.ref.bankbranch.ReadRefBankBranchUseCase;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.domain.ref.bank.RefBank;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefBankBranchService implements ReadRefBankBranchUseCase {
    private final RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @Override
    public Collection<RefBankBranch> readAll() {
        log.trace("Reading all bank list.");

        return refBankBranchRepositoryPort.readAll();
    }
}
