package bhutan.eledger.application.service.ref.bankaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.glaccount.ReadGLAccountPartUseCase;
import bhutan.eledger.application.port.in.ref.bankaccount.CreateRefBankAccountUseCase;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.bankaccount.BankAccountGLAccountPart;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefBankAccountService implements CreateRefBankAccountUseCase {

    private final RefBankAccountRepositoryPort refBankAccountRepositoryPort;
    private final RefBankBranchRepositoryPort refBankBranchRepositoryPort;
    private final ReadGLAccountPartUseCase glAccountPartUseCase;
    private final UpdatePrimaryBankAccountService updatePrimaryBankAccountUseCase;

    @Override
    public Long create(CreateRefBankAccountUseCase.CreateBankAccountCommand command) {
        log.trace("Creating bank's account with command: {}", command);

        Boolean primaryFlag = updatePrimaryBankAccountUseCase.updatePrimaryBankAccountUseCase(command.getBankAccountGLAccountPart().getCode(),command.getIsPrimaryForGlAccount());

        RefBankAccount refBankAccount = mapCommandToRefBankBranch(command, primaryFlag);

        validate(refBankAccount);

        log.trace("Persisting bank's account: {}", refBankAccount);

        Long id = refBankAccountRepositoryPort.create(refBankAccount);

        log.debug("Bank's account with id: {} successfully created.", id);

        return id;
    }

    private RefBankAccount mapCommandToRefBankBranch(CreateBankAccountCommand command, Boolean primaryFlag) {

        return RefBankAccount.withoutId(
                command.getBranchId(),
                command.getCode(),
                ValidityPeriod.of(
                        command.getStartOfValidity(),
                        command.getEndOfValidity()
                ),
                Multilingual.fromMap(command.getDescriptions()),
                primaryFlag,
                BankAccountGLAccountPart.withoutId(
                        command.getBankAccountGLAccountPart().getCode()
                )
        );
    }


    void validate(RefBankAccount refBankAccount) {
        //todo replace existence checks by one method
        if (refBankAccountRepositoryPort.isOpenBankAccountExists(refBankAccount)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("AccNumber", "Bank's branch with account number: [" + refBankAccount.getCode() + "] already exists.")
            );
        }
        if (!refBankBranchRepositoryPort.existsById(refBankAccount.getBranchId())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("BranchId", "Branch with Id: [" + refBankAccount.getBranchId() + "] does not exists.")
            );

        }
        if (!glAccountPartUseCase.existsByFullCode(refBankAccount.getBankAccountGLAccountPart().getCode())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Code", "Gl Account with full code: [" + refBankAccount.getBankAccountGLAccountPart().getCode() + "] does not exists.")
            );

        }


    }


}
