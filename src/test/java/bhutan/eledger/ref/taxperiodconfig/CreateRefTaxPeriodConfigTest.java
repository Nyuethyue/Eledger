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

import java.time.LocalDate;
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

    private static final long MONTHLY = 1;// 12 rows
    private static final long QUARTERLY = 2; // 4 rows
    private static final long FORTNIGHTLY = 3; // 24 rows

    static final String GST_TAX_TYPE = "11411";// 5
    static final String EET_TAX_TYPE = "11421";// 11

    @Test
    void createTest() {
        LocalDate validFrom = LocalDate.now();
        LocalDate validTo = LocalDate.now();
        LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand generateCommand =
                new LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand(
                        GST_TAX_TYPE,
                        2022,
                        MONTHLY,
                        44L,
                        11,
                        11,
                        validFrom,
                        validTo,
                        false);

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
