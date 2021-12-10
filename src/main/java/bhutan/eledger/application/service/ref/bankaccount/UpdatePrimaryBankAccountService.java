package bhutan.eledger.application.service.ref.bankaccount;

import bhutan.eledger.application.port.in.ref.bankaccount.ReadRefBankAccountUseCase;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UpdatePrimaryBankAccountService {

    private final ReadRefBankAccountUseCase readRefBankAccountUseCase;
    private final RefBankAccountRepositoryPort refBankAccountRepositoryPort;


    Boolean updatePrimaryBankAccountUseCase(String code,Boolean flag) {

        //To get data by selected branch id, gl part (full code) and primary status.
        Long primaryAccountId = readRefBankAccountUseCase.readIdByGlCodeAndFlag(code, true);

        if (primaryAccountId == null) {
            return true;
        }
        if (flag) {
            refBankAccountRepositoryPort.setPrimaryFlagById(primaryAccountId, false);
        }

        return flag;
    }
}
