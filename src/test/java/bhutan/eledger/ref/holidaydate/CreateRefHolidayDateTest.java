package bhutan.eledger.ref.holidaydate;

import bhutan.eledger.application.port.in.ref.holidaydate.CreateRefHolidayDateUseCase;
import bhutan.eledger.application.port.out.ref.holidaydate.RefHolidayDateRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateRefHolidayDateTest {

    @Autowired
    private CreateRefHolidayDateUseCase createHolidayDateUseCase;

    @Autowired
    private RefHolidayDateRepositoryPort holidayDateRepositoryPort;

    @AfterEach
    void afterEach() {
        holidayDateRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        var holidayDates = createHolidayDateUseCase.create(
                new CreateRefHolidayDateUseCase.CreateRefHolidayDateCommand(
                        Set.of(
                                new CreateRefHolidayDateUseCase.RefHolidayDateCommand(
                                        "2022",
                                        MonthDay.now(),
                                        MonthDay.now(),
                                        LocalDate.now().plusDays(1),
                                        LocalDate.now().plusDays(1),
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
                new CreateRefHolidayDateUseCase.CreateRefHolidayDateCommand(
                        Set.of(
                                new CreateRefHolidayDateUseCase.RefHolidayDateCommand(
                                        "2022",
                                        MonthDay.now(),
                                        MonthDay.now(),
                                        LocalDate.now().plusDays(1),
                                        LocalDate.now().plusDays(1),
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
