package bhutan.eledger.ref.nonworkingdays;

import bhutan.eledger.application.port.in.ref.nonworkingdays.CreateRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.out.ref.nonworkingdays.RefNonWorkingDaysRepositoryPort;
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
class CreateRefNonWorkingDaysTest {

    @Autowired
    private CreateRefNonWorkingDaysUseCase createRefNonWorkingDaysUseCase;

    @Autowired
    private RefNonWorkingDaysRepositoryPort refNonWorkingDaysRepositoryPort;

    @AfterEach
    void afterEach() {
        refNonWorkingDaysRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        var nonWorkingDays = createRefNonWorkingDaysUseCase.create(
                new CreateRefNonWorkingDaysUseCase.CreateRefNonWorkingDaysCommand(
                        Set.of(
                                new CreateRefNonWorkingDaysUseCase.RefNonWorkingDayCommand(
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
        Assertions.assertNotNull(nonWorkingDays);
        Assertions.assertFalse(nonWorkingDays.isEmpty());
        Assertions.assertNotNull(nonWorkingDays.iterator().next().getId());
    }

    @Test
    void readTest() {
        var nonWorkingDays = createRefNonWorkingDaysUseCase.create(
                new CreateRefNonWorkingDaysUseCase.CreateRefNonWorkingDaysCommand(
                        Set.of(
                                new CreateRefNonWorkingDaysUseCase.RefNonWorkingDayCommand(
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

        var nonWorkingDaysOptional = refNonWorkingDaysRepositoryPort.readById(nonWorkingDays.iterator().next().getId());
        Assertions.assertTrue(nonWorkingDaysOptional.isPresent());
        var nonWorkingDay = nonWorkingDaysOptional.get();
        Assertions.assertNotNull(nonWorkingDay);
        Assertions.assertNotNull(nonWorkingDay.getDescription());
    }
}
