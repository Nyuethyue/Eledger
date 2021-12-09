package bhutan.eledger.application.service.ref.bankaccount;

import bhutan.eledger.application.port.in.ref.bankaccount.ReadRefBankAccountUseCase;
import bhutan.eledger.application.port.in.ref.bankaccount.UpdateRefBankAccountUseCase;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpdateBankAccountUseCase implements UpdateRefBankAccountUseCase {

    private final UpdatePrimaryBankAccountUseCase updatePrimaryBankAccountUseCase;
    private final ReadRefBankAccountUseCase readRefBankAccountUseCase;
    private final RefBankAccountRepositoryPort refBankAccountRepositoryPort;

    @Override
    public void updatePrimaryBankAccount(Long id) {
        log.trace("Updating flag for bank account with id: {} id");
        RefBankAccount refBankAccount = readRefBankAccountUseCase.readById(id);
        updatePrimaryBankAccountUseCase.updatePrimaryBankAccountUseCase(refBankAccount.getBankAccountGLAccountPart().getCode(), true);
        refBankAccountRepositoryPort.setPrimaryFlagById(id, true);
    }
}
