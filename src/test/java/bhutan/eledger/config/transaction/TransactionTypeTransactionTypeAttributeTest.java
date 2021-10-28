package bhutan.eledger.config.transaction;

import bhutan.eledger.application.port.in.config.transaction.*;
import bhutan.eledger.application.port.out.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.config.transaction.TransactionTypeAttribute;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionTypeTransactionTypeAttributeTest {

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


    @Autowired
    private TransactionTypeTransactionTypeAttributesRelationUseCase transactionTypeTransactionTypeAttributesRelationUseCase;

    @Autowired
    private TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort;

    @Autowired
    private CreateTransactionTypeUseCase createTransactionTypeUseCase;

    @Autowired
    private TransactionTypeRepositoryPort transactionTypeRepositoryPort;

    @Autowired
    private ReadTransactionTypeUseCase readTransactionTypeUseCase;

    @Autowired
    private ReadTransactionTypeAttributeUseCase readTransactionTypeAttributeUseCase;


    @AfterEach
    void afterEach() {
        transactionTypeTransactionTypeAttributeRepositoryPort.deleteAll();
        transactionTypeRepositoryPort.deleteAll();
        transactionTypeAttributeRepositoryPort.deleteAll();
    }

    @Test
    void addTest() {

        Long transactionTypeId = createTransactionTypeUseCase.create(
                new CreateTransactionTypeUseCase.CreateTransactionTypeCommand(
                        "LIABD",
                        descriptionMap
                )
        );

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

        transactionTypeTransactionTypeAttributesRelationUseCase.addTransactionTypeAttributesToTransactionTypeByIds(
                transactionTypeId,
                new TransactionTypeTransactionTypeAttributesRelationUseCase.TransactionAttributeCommand(
                        transactionTypeAttributes.stream().map(TransactionTypeAttribute::getId).collect(Collectors.toList())
                )
        );

        var transactionTypeWithAttributes = readTransactionTypeUseCase.readWithAttributesById(transactionTypeId);

        Assertions.assertNotNull(transactionTypeWithAttributes);
        Assertions.assertNotNull(transactionTypeWithAttributes.getAttributes());
        Assertions.assertEquals(2, transactionTypeWithAttributes.getAttributes().size());

        var ttattr = readTransactionTypeAttributeUseCase.readAllByTransactionTypeId(transactionTypeId);

        Assertions.assertNotNull(ttattr);
        Assertions.assertEquals(2, ttattr.size());

        transactionTypeTransactionTypeAttributesRelationUseCase.removeTransactionTypeAttributesFromTransactionTypeByIds(
                transactionTypeId,
                new TransactionTypeTransactionTypeAttributesRelationUseCase.TransactionAttributeCommand(
                        transactionTypeWithAttributes.getAttributes().stream().map(TransactionTypeAttribute::getId).findAny().map(List::of).get()
                )
        );

        var transactionTypeWithAttributesAfterRemove = readTransactionTypeUseCase.readWithAttributesById(transactionTypeId);

        Assertions.assertNotNull(transactionTypeWithAttributesAfterRemove);
        Assertions.assertNotNull(transactionTypeWithAttributesAfterRemove.getAttributes());
        Assertions.assertEquals(1, transactionTypeWithAttributesAfterRemove.getAttributes().size());
    }
}
