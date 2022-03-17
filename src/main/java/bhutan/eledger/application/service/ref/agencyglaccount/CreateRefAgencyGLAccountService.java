package bhutan.eledger.application.service.ref.agencyglaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.agencyglaccount.CreateRefAgencyGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.application.port.out.ref.agencyglaccount.RefAgencyGLAccountRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefAgencyGLAccountService implements CreateRefAgencyGLAccountUseCase {
    private final RefAgencyGLAccountRepositoryPort refAgencyGLAccountRepositoryPort;
    private final GLAccountRepositoryPort glAccountRepositoryPort;

    @Override
    public Collection<RefAgencyGLAccount> create(CreateRefAgencyGLAccountUseCase.CreateAgencyGlAccountCommand command) {
        log.trace("Creating agency and gl account with command: {}", command);

        validate(command);

        var agencyGlAccounts = mapCommandToRefAgencyGlAccounts(command);

        log.trace("Persisting agency and gl account parts: {}", agencyGlAccounts);

        var persistedAgencyGlAccounts = refAgencyGLAccountRepositoryPort.create(agencyGlAccounts);

        log.debug("Agency and gL account with codes: {} successfully created.", () -> persistedAgencyGlAccounts.stream().map(RefAgencyGLAccount::getCode).collect(Collectors.toUnmodifiableList()));


        return persistedAgencyGlAccounts;
    }

    private void validate(CreateAgencyGlAccountCommand command) {
        boolean isGlAccountCodeExist = glAccountRepositoryPort.existsByCode(command
                .getGlAccounts()
                .stream()
                .map(agencyGlAccountCommand -> agencyGlAccountCommand.getCode())
                .collect(Collectors.toList()));
        if (!isGlAccountCodeExist) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Code", "One or more gl account does not exists.")
            );
        }
    }

    private Collection<RefAgencyGLAccount> mapCommandToRefAgencyGlAccounts(CreateAgencyGlAccountCommand command) {

        String agencyCode = command.getAgencyCode();

        return command.getGlAccounts()
                .stream()
                .map(agencyGlAccountCommand -> {
                    return RefAgencyGLAccount.withoutId(
                            agencyGlAccountCommand.getCode(),
                            agencyCode,
                            ValidityPeriod.of(
                                    command.getStartOfValidity(),
                                    command.getEndOfValidity()
                            )
                    );
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
