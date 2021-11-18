package bhutan.eledger.eledger.config.property;

import bhutan.eledger.application.port.in.eledger.config.property.CreatePropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.ReadPropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.UpdatePropertiesUseCase;
import bhutan.eledger.application.port.out.eledger.config.property.PropertyRepositoryPort;
import bhutan.eledger.domain.eledger.config.property.Property;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdatePropertiesTest {

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

    @Autowired
    private UpdatePropertiesUseCase updatePropertiesUseCase;

    @Autowired
    private ReadPropertyUseCase readPropertyUseCase;

    Long existedWithoutEndDateRecordId;
    Long existedWithEndDateRecordId;

    @BeforeEach
    void beforeEach() {
        var properties = createPropertyUseCase.create(
                new CreatePropertyUseCase.CreatePropertiesCommand(
                        List.of(
                                new CreatePropertyUseCase.PropertyCommand(
                                        propertyCode1, "TEST", LocalDate.now().plusDays(1), descriptionMap, 1
                                ),
                                new CreatePropertyUseCase.PropertyCommand(
                                        propertyCode2, "TEST", LocalDate.now().plusDays(2), descriptionMap, 3
                                )
                        )
                )
        );

        existedWithoutEndDateRecordId = properties
                .stream()
                .filter(property -> property.getValidityPeriod().getEnd() == null)
                .findFirst()
                .get().getId();
    }

    @AfterEach
    void afterEach() {
        propertyRepositoryPort.deleteAll();
    }

    @Test
    void testWithoutEndDateRecordUpdate() {
        var propertyCommand = new UpdatePropertiesUseCase.UpdatePropertyCommand(
                existedWithoutEndDateRecordId,
                LocalDate.now().plusDays(2),
                "UPDATED VALUE",
                null
        );

        UpdatePropertiesUseCase.UpdatePropertiesCommand command = new UpdatePropertiesUseCase.UpdatePropertiesCommand(
                List.of(
                        propertyCommand
                )
        );


        Collection<Long> ids = updatePropertiesUseCase.update(
                command
        );

        Property property = readPropertyUseCase.readById(existedWithoutEndDateRecordId);

        Assertions.assertEquals(propertyCommand.getStartOfValidity(), property.getValidityPeriod().getEnd());

        Property newProperty = readPropertyUseCase.readById(ids.iterator().next());

        Assertions.assertEquals(property.getValidityPeriod().getEnd(), newProperty.getValidityPeriod().getStart());
        Assertions.assertTrue(newProperty.getDescription().hasNotTranslations());
    }

}
