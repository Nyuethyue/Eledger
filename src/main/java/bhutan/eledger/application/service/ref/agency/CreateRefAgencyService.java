package bhutan.eledger.application.service.ref.agency;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.agency.CreateRefAgencyUseCase;
import bhutan.eledger.application.port.out.ref.agency.RefAgencyRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.agency.RefAgency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefAgencyService implements CreateRefAgencyUseCase{

    private final RefAgencyRepositoryPort refAgencyRepositoryPort;

    @Override
    public Long create(CreateRefAgencyUseCase.CreateRefAgencyCommand command) {
        log.trace("Creating agency with command: {}", command);

        RefAgency refAgency = mapCommandToRefAgency(command);

        validate(refAgency);

        log.trace("Persisting agency: {}", refAgency);

        Long id = refAgencyRepositoryPort.create(refAgency);

        log.debug("Agency with id: {} successfully created.", id);

        return id;
    }

    private RefAgency mapCommandToRefAgency(CreateRefAgencyUseCase.CreateRefAgencyCommand command) {
        return RefAgency.withoutId(
                command.getCode(),
                ValidityPeriod.of(
                        command.getStartOfValidity(),
                        command.getEndOfValidity()
                ),
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(RefAgency refAgency) {
        if (refAgencyRepositoryPort.isOpenAgencyExists(refAgency)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Code", "Agency with code: [" + refAgency.getCode() + "] already exists.")
            );
        }
    }
}
