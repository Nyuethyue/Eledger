package bhutan.eledger.eledger.config.property;

import bhutan.eledger.application.port.in.eledger.config.property.CreatePropertyUseCase;
import bhutan.eledger.application.port.out.eledger.config.property.PropertyRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreatePropertiesTest {

    private final String propertyCode1 = "DAILY_PERCENT";
    private final String propertyCode2 = "T_R_GST";
    private final Map<String, String> descriptionMap = Map.of(
            "en", "Text en",
            "dz", "Text dz"
    );
    @Autowired
    private CreatePropertyUseCase createPropertyUseCase;
    @Autowired
    private PropertyRepositoryPort propertyRepositoryPort;

    @AfterEach
    void afterEach() {
        propertyRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        var properties = createPropertyUseCase.create(
                new CreatePropertyUseCase.CreatePropertiesCommand(
                        List.of(
                                new CreatePropertyUseCase.PropertyCommand(
                                        propertyCode1, "TEST", LocalDate.now().plusDays(1), descriptionMap, 1
                                ),
                                new CreatePropertyUseCase.PropertyCommand(
                                        propertyCode2, "TEST", LocalDate.now().plusDays(1), descriptionMap, 3
                                )
                        )
                )
        );

        Assertions.assertNotNull(properties);

        Assertions.assertFalse(properties.isEmpty());

        Assertions.assertEquals(2, properties.size());
    }

    @Test
    void readTest() {

        var properties = createPropertyUseCase.create(
                new CreatePropertyUseCase.CreatePropertiesCommand(
                        List.of(
                                new CreatePropertyUseCase.PropertyCommand(
                                        propertyCode1, "TEST", LocalDate.now().plusDays(1), descriptionMap, 1
                                ),
                                new CreatePropertyUseCase.PropertyCommand(
                                        propertyCode2, "TEST", LocalDate.now().plusDays(1), descriptionMap, 3
                                )
                        )
                )
        );


        properties.forEach(tta -> {

            var propertyOptional = propertyRepositoryPort.readById(tta.getId());

            Assertions.assertTrue(propertyOptional.isPresent());

            var property = propertyOptional.get();

            var description = property.getDescription();

            Assertions.assertNotNull(property.getDescription());

            var enTranslation = description.translationValue("en");
            var dzTranslation = description.translationValue("dz");

            Assertions.assertTrue(enTranslation.isPresent());
            Assertions.assertTrue(dzTranslation.isPresent());

            Assertions.assertEquals(descriptionMap.get("en"), enTranslation.get());
            Assertions.assertEquals(descriptionMap.get("dz"), dzTranslation.get());

        });


        var propertyIterator = properties.iterator();

        Long firstId = propertyIterator.next().getId();

        Long secondId = propertyIterator.next().getId();

        Assertions.assertEquals(propertyCode1, propertyRepositoryPort.readById(firstId).get().getCode());
        Assertions.assertEquals(propertyCode2, propertyRepositoryPort.readById(secondId).get().getCode());
    }
}
