package bhutan.eledger.ref.holidaydate;

import bhutan.eledger.application.port.in.ref.holidaydate.CreateHolidayDateUseCase;
import bhutan.eledger.application.port.out.ref.holidaydate.HolidayDateRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateHolidayDateTest {

    @Autowired
    private CreateHolidayDateUseCase createHolidayDateUseCase;

    @Autowired
    private HolidayDateRepositoryPort holidayDateRepositoryPort;

    @Test
    void createTest() {
        var holidayDates = createHolidayDateUseCase.create(
                new CreateHolidayDateUseCase.CreateHolidayDateCommand(
                        Set.of(
                                new CreateHolidayDateUseCase.HolidayDateCommand(
                                        "2022",
                                        LocalDate.now().plusDays(1),
                                        LocalDate.now().plusDays(1),
                                        true,
                                        Map.of("en", "Holiday A")
                                )
                        )
                )
        );
        Assertions.assertNotNull(holidayDates);
        Assertions.assertFalse(holidayDates.isEmpty());
        Assertions.assertNotNull(holidayDates.iterator().next().getId());
    }

    @Test
    void readTest() {
        var holidayDates = createHolidayDateUseCase.create(
                new CreateHolidayDateUseCase.CreateHolidayDateCommand(
                        Set.of(
                                new CreateHolidayDateUseCase.HolidayDateCommand(
                                        "2022",
                                        LocalDate.now().plusDays(1),
                                        LocalDate.now().plusDays(1),
                                        true,
                                        Map.of("en", "Holiday A")
                                )
                        )
                )
        );

        var holidayDateOptional = holidayDateRepositoryPort.readById(holidayDates.iterator().next().getId());
        Assertions.assertTrue(holidayDateOptional.isPresent());
        var holidayDate = holidayDateOptional.get();
        Assertions.assertNotNull(holidayDate);
        Assertions.assertNotNull(holidayDate.getDescription());
    }
}
