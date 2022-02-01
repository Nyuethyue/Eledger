package bhutan.eledger.application.service.ref.nonworkingdays;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.nonworkingdays.CreateRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.out.ref.nonworkingdays.RefNonWorkingDaysRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateNonWorkingDaysService implements CreateRefNonWorkingDaysUseCase {

    private final RefNonWorkingDaysRepositoryPort refNonWorkingDaysRepositoryPort;

    @Override
    public Collection<RefNonWorkingDays> create(CreateRefNonWorkingDaysCommand command) {


        log.trace("Creating non working days with command: {}", command);

        var nonWorkingDays = makeNonWorkingDays(command);

        log.trace("Persisting non working days: {}", nonWorkingDays);

        var persistedNonWorkingDays = refNonWorkingDaysRepositoryPort.create(nonWorkingDays);

        log.debug("Non working days with ids: {} successfully created.", () -> persistedNonWorkingDays.stream().map(RefNonWorkingDays::getId).collect(Collectors.toUnmodifiableList()));


        return persistedNonWorkingDays;
    }


    private Collection<RefNonWorkingDays> makeNonWorkingDays(CreateRefNonWorkingDaysCommand command) {

        return command.getNonWorkingDays()
                .stream()
                .map(nonWorkingDayCommand -> {
                    return RefNonWorkingDays.withoutId(
                            UUID.randomUUID().toString(),
                            nonWorkingDayCommand.getStartDay(),
                            nonWorkingDayCommand.getEndDay(),
                            ValidityPeriod.of(
                                    nonWorkingDayCommand.getStartOfValidity(),
                                    nonWorkingDayCommand.getEndOfValidity()
                            ),
                            Multilingual.fromMap(nonWorkingDayCommand.getDescriptions()));
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
