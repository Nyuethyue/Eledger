package bhutan.eledger.application.service.ref.bankbranch;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefBankBranchService implements CreateRefBankBranchUseCase{

    private final RefBankBranchRepositoryPort refBankBranchRepositoryPort;
    private final RefBankRepositoryPort refBankRepositoryPort;

    @Override
    public Long create(CreateRefBankBranchUseCase.CreateBranchCommand command) {
        log.trace("Creating bank's branch with command: {}", command);

        RefBankBranch refBankBranch = mapCommandToRefBankBranch(command);

        validate(refBankBranch);

        log.trace("Persisting bank's branch: {}", refBankBranch);

        Long id = refBankBranchRepositoryPort.create(refBankBranch);

        log.debug("Bank's branch with id: {} successfully created.", id);

        return id;
    }

    private RefBankBranch mapCommandToRefBankBranch(CreateRefBankBranchUseCase.CreateBranchCommand command) {
        return RefBankBranch.withoutId(
                command.getCode(),
                command.getBranchCode(),
                command.getAddress(),
                ValidityPeriod.of(
                        command.getStartOfValidity(),
                        command.getEndOfValidity()
                ),
                command.getBankId(),
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(RefBankBranch refBankBranch) {
        //todo replace existence checks by one method
        if (refBankBranchRepositoryPort.isOpenBranchExists(refBankBranch)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("code", "Bank's branch with code: [" + refBankBranch.getCode() + "] already exists.")
            );
        }
        if (!refBankRepositoryPort.existsById(refBankBranch.getBankId())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("bankId", "Bank with Id: [" + refBankBranch.getBankId() + "] does not exists.")
            );
        }
    }

}
