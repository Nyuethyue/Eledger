package bhutan.eledger.ref.rrcocashcounter;

import bhutan.eledger.application.port.in.ref.rrcocashcounter.CreateRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.out.ref.rrcocashcounter.RefRRCOCashCounterRepositoryPort;
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
public class CreateRefRRCOCashCounterTest {

    @Autowired
    private CreateRefRRCOCashCountersUseCase createRefRRCOCashCountersUseCase;

    @Autowired
    private RefRRCOCashCounterRepositoryPort refRRCOCashCounterRepositoryPort;

    @AfterEach
    void afterEach() {
        refRRCOCashCounterRepositoryPort.deleteAll();
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

        var rrcoCC = refRRCOCashCounterRepositoryPort.readById(id);

        Assertions.assertTrue(rrcoCC.isPresent());

        var rrco = rrcoCC.get();
        Assertions.assertNotNull(rrco);
        Assertions.assertNotNull(rrco.getDescription());
    }
}
