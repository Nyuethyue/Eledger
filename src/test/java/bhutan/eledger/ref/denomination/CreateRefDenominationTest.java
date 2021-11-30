package bhutan.eledger.ref.denomination;

import bhutan.eledger.application.port.in.ref.denomination.CreateRefDenominationUseCase;
import bhutan.eledger.application.port.out.ref.denomination.RefDenominationRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateRefDenominationTest {

    @Autowired
    private CreateRefDenominationUseCase createRefDenominationUseCase;

    @Autowired
    private RefDenominationRepositoryPort refDenominationRepositoryPort;

    @AfterEach
    void afterEach() {
        refDenominationRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createRefDenominationUseCase.create(
                new CreateRefDenominationUseCase.CreateDenominationCommand((
                        "0.01")));
        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        Long id = createRefDenominationUseCase.create(
                new CreateRefDenominationUseCase.CreateDenominationCommand(
                        "0.01"));
        var denominationOptional = refDenominationRepositoryPort.readById(id);

        var denomination = denominationOptional.get();
        Assertions.assertNotNull(denomination);
        Assertions.assertNotNull(denomination.getDescription());
    }
}
