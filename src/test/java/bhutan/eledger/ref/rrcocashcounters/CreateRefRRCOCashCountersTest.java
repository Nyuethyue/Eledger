package bhutan.eledger.ref.rrcocashcounters;

import bhutan.eledger.application.port.in.ref.rrcocashcounters.CreateRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.out.ref.rrcocashcounters.RefRRCOCashCountersRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class CreateRefRRCOCashCountersTest {

    @Autowired
    private CreateRefRRCOCashCountersUseCase createRefRRCOCashCountersUseCase;

    @Autowired
    private RefRRCOCashCountersRepositoryPort refRRCOCashCountersRepositoryPort;

    @AfterEach
    void afterEach() {
        refRRCOCashCountersRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createRefRRCOCashCountersUseCase.create(
                new CreateRefRRCOCashCountersUseCase.CreateRefRRCOCashCountersCommand(
                        "C-0983",
                        LocalDate.now().plusDays(1),
                        null,
                        Map.of("en", "RRCO")
                )
        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        Long id = createRefRRCOCashCountersUseCase.create(
                new CreateRefRRCOCashCountersUseCase.CreateRefRRCOCashCountersCommand(
                        "g-9939",
                        LocalDate.now().plusDays(1),
                        null,
                        Map.of("en", "RRCO")
                )
        );

        var rrcoCC = refRRCOCashCountersRepositoryPort.readById(id);

        Assertions.assertTrue(rrcoCC.isPresent());

        var rrco = rrcoCC.get();
        Assertions.assertNotNull(rrco);
        Assertions.assertNotNull(rrco.getDescription());
    }
}
