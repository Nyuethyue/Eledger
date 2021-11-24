package bhutan.eledger.application.service.ref.bankaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.bankaccount.ReadRefBankAccountUseCase;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
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
class ReadRefBankAccountService implements ReadRefBankAccountUseCase {

    private final RefBankAccountRepositoryPort refBankAccountRepositoryPort;

    @Override
    public Collection<RefBankAccount> readAll() {
        log.trace("Reading all bank's account list.");

        return refBankAccountRepositoryPort.readAll();
    }

    @Override
    public RefBankAccount readById(Long id) {
        log.trace("Reading bank's branch by id: {}", id);

        return refBankAccountRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Bank's account by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<RefBankAccount> readAllByBranchId(Long branchId) {
        log.trace("Reading all account information by branch id.");

        return refBankAccountRepositoryPort.readAllByBranchId(branchId);
    }
}
