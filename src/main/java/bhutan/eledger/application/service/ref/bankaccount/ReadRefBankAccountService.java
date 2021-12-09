package bhutan.eledger.application.service.ref.bankaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.bankaccount.ReadRefBankAccountUseCase;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
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

    @Override
    public RefBankAccount readByCode(String code) {
        log.trace("Reading bank's branch by account number: {}", code);

        return refBankAccountRepositoryPort.readByCode(code)
                .orElseThrow(() ->
                        new RecordNotFoundException("Bank's account by account number: [" + code + "] not found.")
                );
    }

    @Override
    public Long readIdByGlCodeAndFlag(String glCode, Boolean flag) {
        log.trace("Reading account information by gl code and primary flag.");

        return refBankAccountRepositoryPort.readIdByGlCodeAndFlag(glCode, flag);
    }

    @Override
    public RefBankAccount readPrimaryAccByGlPartFullCode(String glPartFullCode) {
        log.trace("Reading primary account information by gl code and primary flag.");

        Long id = refBankAccountRepositoryPort.readIdByGlCodeAndFlag(glPartFullCode, true);

        if (id == null) {
            throw new IllegalArgumentException("Primary Account is not set.");
        }

        return refBankAccountRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Primary Bank's account by gl code: [" + glPartFullCode + "] not found.")
                );

    }
}
