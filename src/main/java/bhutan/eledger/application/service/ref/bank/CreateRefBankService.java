package bhutan.eledger.application.service.ref.bank;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefBankService implements CreateRefBankUseCase {

    private final RefBankRepositoryPort refBankRepositoryPort;

    @Override
    public Long create(CreateRefBankCommand command) {
        log.trace("Creating bank with command: {}", command);

        RefBank refBank = mapCommandToRefBank(command);

        validate(refBank);

        log.trace("Persisting bank: {}", refBank);

        Long id = refBankRepositoryPort.create(refBank);

        log.debug("Bank with id: {} successfully created.", id);

        return id;
    }

    private RefBank mapCommandToRefBank(CreateRefBankUseCase.CreateRefBankCommand command) {
        return RefBank.withoutId(
                command.getCode(),
                ValidityPeriod.withOnlyStartOfValidity(
                  command.getStartOfValidity()
                ),
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(RefBank refBank) {
        if (refBankRepositoryPort.isOpenBankExists(refBank)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Code", "Bank with BFSC code: [" + refBank.getCode() + "] already exists.")
            );
        }
    }
}
