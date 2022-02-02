package bhutan.eledger.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.LinkedList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateRefTaxPeriodConfigTest {
    @Autowired
    private LoadGenTaxPeriodConfigUseCase loadGenTaxPeriodConfigUseCase;

    @Autowired
    private UpsertTaxPeriodUseCase upsertTaxPeriodUseCase;


    @Autowired
    private RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    @AfterEach
    void afterEach() {
        refTaxPeriodRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand generateCommand =
                new LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand(
                        "111",
                        2022,
                        33L,
                        44L);

        RefTaxPeriodConfig configGenerated = loadGenTaxPeriodConfigUseCase.loadGen(generateCommand);
        Assertions.assertNotNull(configGenerated);

/*
        private final Long id;
        private final Long taxTypeId;
        private Integer calendarYear;
        private final Long taxPeriodTypeId;
        private final Long transactionTypeId;
        private Long dueDateCountForReturnFiling;
        private Long dueDateCountForPayment;
        private LocalDate validFrom;
        private LocalDate validTo;
        private Boolean considerNonWorkingDays;

 */

        Collection<UpsertTaxPeriodUseCase.TaxPeriodRecordCommand> records = new LinkedList<>();
        UpsertTaxPeriodUseCase.UpsertTaxPeriodCommand upsertCommand =
                new UpsertTaxPeriodUseCase.UpsertTaxPeriodCommand(
                        configGenerated.getId(),
                        configGenerated.getTaxTypeCode(),
                        configGenerated.getCalendarYear(),
                        configGenerated.getTaxPeriodTypeId(),
                        configGenerated.getTransactionTypeId(),
                        configGenerated.getDueDateCountForReturnFiling(),
                        configGenerated.getDueDateCountForPayment(),
                        configGenerated.getValidFrom(),
                        configGenerated.getValidTo(),
                        configGenerated.getConsiderNonWorkingDays(),
                        records
                );

        long recordId = upsertTaxPeriodUseCase.upsert(upsertCommand);

        RefTaxPeriodConfig configLoaded = loadGenTaxPeriodConfigUseCase.loadGen(generateCommand);
        Assertions.assertNotNull(configLoaded);
        Assertions.assertNotNull(configLoaded.getId());
    }
}
