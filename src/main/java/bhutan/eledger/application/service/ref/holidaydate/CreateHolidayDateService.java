package bhutan.eledger.application.service.ref.holidaydate;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.holidaydate.CreateHolidayDateUseCase;
import bhutan.eledger.application.port.out.ref.holidaydate.HolidayDateRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateHolidayDateService implements CreateHolidayDateUseCase {

    private final HolidayDateRepositoryPort holidayDateRepositoryPort;

    @Override
    public Collection<HolidayDate> create(CreateHolidayDateCommand command) {

        log.trace("Creating holiday date with command: {}", command);

        validate(command);

        var holidayDate = makeHolidayDate(command);

        log.trace("Persisting holiday date: {}", holidayDate);

        var persistedHolidayDate = holidayDateRepositoryPort.create(holidayDate);

        log.debug("Holiday date with ids: {} successfully created.", () -> persistedHolidayDate.stream().map(HolidayDate::getId).collect(Collectors.toUnmodifiableList()));


        return persistedHolidayDate;
    }

    private void validate(CreateHolidayDateCommand holidayDates) {
        holidayDates.getHolidayDates().stream().forEach(holidayDateCommand -> {
                    LocalDate holidayStartDate = holidayDateCommand.getHolidayStartDate();
                    LocalDate holidayEndDate = holidayDateCommand.getHolidayEndDate();
                    if (LocalDate.now().isAfter(holidayStartDate) || LocalDate.now().isEqual(holidayStartDate) ||
                            LocalDate.now().isAfter(holidayEndDate) || LocalDate.now().isEqual(holidayEndDate)
                    ) {
                        throw new ViolationException(
                                new ValidationError()
                                        .addViolation("holidayStartDate,holidayEndDate", "Holiday start date and end date should be future date.")
                        );
                    }
                }
        );
    }

    private Collection<HolidayDate> makeHolidayDate(CreateHolidayDateCommand command) {

        return command.getHolidayDates()
                .stream()
                .map(holidayDateCommand -> {

                    return HolidayDate.withoutId(
                            holidayDateCommand.getYear(),
                            ValidityPeriod.of(
                                    holidayDateCommand.getHolidayStartDate(),
                                    holidayDateCommand.getHolidayEndDate()
                            ),
                            holidayDateCommand.getIsValidForOneYear(),
                            Multilingual.fromMap(holidayDateCommand.getDescriptions()));
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
