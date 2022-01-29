package bhutan.eledger.application.service.ref.holidaydate;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.holidaydate.CreateRefHolidayDateUseCase;
import bhutan.eledger.application.port.out.ref.holidaydate.RefHolidayDateRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
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
class CreateHolidayDateService implements CreateRefHolidayDateUseCase {

    private final RefHolidayDateRepositoryPort refHolidayDateRepositoryPort;

    @Override
    public Collection<RefHolidayDate> create(CreateRefHolidayDateCommand command) {


        log.trace("Creating holiday date with command: {}", command);

        var holidayDate = makeHolidayDate(command);

        log.trace("Persisting holiday date: {}", holidayDate);

        var persistedHolidayDate = refHolidayDateRepositoryPort.create(holidayDate);

        log.debug("Holiday date with ids: {} successfully created.", () -> persistedHolidayDate.stream().map(RefHolidayDate::getId).collect(Collectors.toUnmodifiableList()));


        return persistedHolidayDate;
    }


    private Collection<RefHolidayDate> makeHolidayDate(CreateRefHolidayDateCommand command) {

        return command.getHolidayDates()
                .stream()
                .map(holidayDateCommand -> {
                    return RefHolidayDate.withoutId(
                            holidayDateCommand.getYear(),
                            holidayDateCommand.getStartOfHoliday(),
                            holidayDateCommand.getEndOfHoliday(),
                            ValidityPeriod.of(
                                    holidayDateCommand.getStartOfValidity(),
                                    holidayDateCommand.getEndOfValidity()
                            ),
                            Multilingual.fromMap(holidayDateCommand.getDescriptions()));
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
