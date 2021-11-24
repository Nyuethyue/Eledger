package bhutan.eledger.eledger.config.transaction;

import bhutan.eledger.application.port.in.eledger.config.property.CreatePropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeAttributeUseCase;
import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeAttributeRepositoryPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateTransactionTypeAttributeTest {

    @Autowired
    private CreateTransactionTypeAttributeUseCase createTransactionTypeAttributeUseCase;

    @Autowired
    private TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort;

    private final String transactionTypeAttrTpnName = "TPN";
    private final String transactionTypeAttrDeadLineName = "DEADLINE";

    private final Map<String, String> descriptionMap = Map.of(
            "en", "Text en",
            "dz", "Text dz"
    );

    @AfterEach
    void afterEach() {
        transactionTypeAttributeRepositoryPort.deleteAll();
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createTest() throws JsonProcessingException {

        System.out.println("*********************************");

        System.out.println(
                objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(
                                new CreateTransactionsUseCase.CreateTransactionsCommand(
                                        "The drn",
                                        new CreateTransactionsUseCase.TaxpayerCommand(
                                                "The tpn",
                                                "The taxpayer name"
                                        ),
                                        Set.of(
                                                new CreateTransactionsUseCase.TransactionCommand(
                                                        "The gl aaccount code",
                                                        LocalDate.now(),
                                                        BigDecimal.TEN,
                                                        1L,
                                                        Set.of(
                                                                new CreateTransactionsUseCase.TransactionAttributeCommand(
                                                                        1L,
                                                                        "Transaction attribute value"
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
        );

        System.out.println(
                objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(
                                new CreatePropertyUseCase.CreatePropertiesCommand(
                                        Set.of(
                                                new CreatePropertyUseCase.PropertyCommand(
                                                        "The code",
                                                        "The value",
                                                        LocalDate.now(),
                                                        Map.of("en", "English dsc"),
                                                        1
                                                )
                                        )
                                )
                        )
        );

        System.out.println("*********************************");

        var transactionTypeAttributes = createTransactionTypeAttributeUseCase.create(
                new CreateTransactionTypeAttributeUseCase.CreateTransactionTypeAttributesCommand(
                        List.of(
                                new CreateTransactionTypeAttributeUseCase.TransactionTypeAttributeCommand(
                                        transactionTypeAttrTpnName, descriptionMap, 1
                                ),
                                new CreateTransactionTypeAttributeUseCase.TransactionTypeAttributeCommand(
                                        transactionTypeAttrDeadLineName, descriptionMap, 3
                                )
                        )
                )
        );

        Assertions.assertNotNull(transactionTypeAttributes);

        Assertions.assertFalse(transactionTypeAttributes.isEmpty());
    }

    @Test
    void readTest() {

        var transactionTypeAttributes = createTransactionTypeAttributeUseCase.create(
                new CreateTransactionTypeAttributeUseCase.CreateTransactionTypeAttributesCommand(
                        List.of(
                                new CreateTransactionTypeAttributeUseCase.TransactionTypeAttributeCommand(
                                        transactionTypeAttrTpnName, descriptionMap, 1
                                ),
                                new CreateTransactionTypeAttributeUseCase.TransactionTypeAttributeCommand(
                                        transactionTypeAttrDeadLineName, descriptionMap, 3
                                )
                        )
                )
        );


        transactionTypeAttributes.forEach(tta -> {

            var transactionTypeAttributeOptional = transactionTypeAttributeRepositoryPort.readById(tta.getId());

            Assertions.assertTrue(transactionTypeAttributeOptional.isPresent());

            var transactionTypeAttribute = transactionTypeAttributeOptional.get();

            var description = transactionTypeAttribute.getDescription();

            Assertions.assertNotNull(transactionTypeAttribute.getDescription());

            var enTranslation = description.translationValue("en");
            var dzTranslation = description.translationValue("dz");

            Assertions.assertTrue(enTranslation.isPresent());
            Assertions.assertTrue(dzTranslation.isPresent());

            Assertions.assertEquals(descriptionMap.get("en"), enTranslation.get());
            Assertions.assertEquals(descriptionMap.get("dz"), dzTranslation.get());

        });


        var transactionTypeAttributeIterator = transactionTypeAttributes.iterator();

        Long firstId = transactionTypeAttributeIterator.next().getId();

        Long secondId = transactionTypeAttributeIterator.next().getId();

        Assertions.assertEquals(transactionTypeAttrTpnName, transactionTypeAttributeRepositoryPort.readById(firstId).get().getName());
        Assertions.assertEquals(transactionTypeAttrDeadLineName, transactionTypeAttributeRepositoryPort.readById(secondId).get().getName());


    }
}
