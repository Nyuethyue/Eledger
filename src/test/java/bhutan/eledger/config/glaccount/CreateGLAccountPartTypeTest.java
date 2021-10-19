package bhutan.eledger.config.glaccount;

import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateGLAccountPartTypeTest {

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @AfterEach
    void afterEach() {
        glAccountPartTypeRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        Integer id = createGLAccountPartTypeUseCase.create(
                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                        1,
                        GLAccountPartTypeUtils.partTypeLanguageCodeToDescriptionByLevel(1)
                )
        );

        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        var languageCodeToDescription = GLAccountPartTypeUtils.partTypeLanguageCodeToDescriptionByLevel(1);

        Integer id = createGLAccountPartTypeUseCase.create(
                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                        1,
                        languageCodeToDescription
                )
        );

        var partTypeOptional = glAccountPartTypeRepositoryPort.readById(id);

        Assertions.assertTrue(partTypeOptional.isPresent());

        var partType = partTypeOptional.get();

        Assertions.assertEquals(1, partType.getLevel());

        var description = partType.getDescription();

        Assertions.assertNotNull(partType.getDescription());

        var enTranslation = description.translationValue("en");
        var btTranslation = description.translationValue("dz");

        Assertions.assertTrue(enTranslation.isPresent());
        Assertions.assertTrue(btTranslation.isPresent());

        Assertions.assertEquals(languageCodeToDescription.get("en"), enTranslation.get());
        Assertions.assertEquals(languageCodeToDescription.get("dz"), btTranslation.get());
    }
}
