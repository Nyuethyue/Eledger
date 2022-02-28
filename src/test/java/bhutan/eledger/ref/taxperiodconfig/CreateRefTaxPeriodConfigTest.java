package bhutan.eledger.ref.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.*;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodConfigRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;

    @Autowired
    private LoadTaxPeriodSegmentsUseCase loadTaxPeriodSegmentsUseCase;

    @Autowired
    private LoadGenTaxPeriodConfigUseCase loadGenTaxPeriodConfigUseCase;

    @Autowired
    private SearchTaxPeriodConfigUseCase searchTaxPeriodConfigUseCase;

    @Autowired
    private UpsertTaxPeriodUseCase upsertTaxPeriodUseCase;


    @Autowired
    private RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void afterEach() {
        refTaxPeriodRepositoryPort.deleteAll();
    }

    static final String GST_TAX_TYPE = "11411";// 5
    static final String EET_TAX_TYPE = "11421";// 11

    static final long TRANSACTION_TYPE_LIABILITY = 1;

    @Test
    void createAllTaxPeriodTypesTest() {
        testTaxPeriodType(TaxPeriodType.MONTHLY);
        testTaxPeriodType(TaxPeriodType.FORTNIGHTLY);
        testTaxPeriodType(TaxPeriodType.QUARTERLY);
        testTaxPeriodType(TaxPeriodType.HALFYEARLY);
        testTaxPeriodType(TaxPeriodType.YEARLY);
    }

    void testTaxPeriodType(TaxPeriodType taxPeriodType) {
        LocalDate validFrom = LocalDate.now();
        LocalDate validTo = LocalDate.now();
        LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand generateCommand =
                new LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand(
                        GST_TAX_TYPE,
                        2022,
                        taxPeriodType.getValue(),
                        TRANSACTION_TYPE_LIABILITY,
                        11,
                        11,
                        validFrom,
                        validTo,
                        false);

        RefTaxPeriodConfig configGenerated = loadGenTaxPeriodConfigUseCase.loadGen(generateCommand);
        validate(configGenerated);

        Collection<UpsertTaxPeriodUseCase.TaxPeriodRecordCommand> records = new LinkedList<>();
        for(TaxPeriodConfigRecord gr : configGenerated.getRecords()) {
            records.add(new UpsertTaxPeriodUseCase.TaxPeriodRecordCommand(
                    gr.getPeriodId(),
                    gr.getPeriodStartDate(),
                    gr.getPeriodEndDate(),
                    gr.getFilingDueDate(),
                    gr.getPaymentDueDate(),
                    gr.getInterestCalcStartDate(),
                    gr.getFineAndPenaltyCalcStartDate(),
                    gr.getValidFrom(),
                    gr.getTaxTypeCode()
            ));
        }

        UpsertTaxPeriodUseCase.UpsertTaxPeriodCommand upsertCommand =
                new UpsertTaxPeriodUseCase.UpsertTaxPeriodCommand(
                        configGenerated.getId(),
                        configGenerated.getTaxTypeCode(),
                        configGenerated.getCalendarYear(),
                        configGenerated.getTaxPeriodCode(),
                        configGenerated.getTransactionTypeId(),
                        configGenerated.getDueDateCountForReturnFiling(),
                        configGenerated.getDueDateCountForPayment(),
                        configGenerated.getValidFrom(),
                        configGenerated.getValidTo(),
                        configGenerated.getConsiderNonWorkingDays(),
                        records
                );

        Long recordId = upsertTaxPeriodUseCase.upsert(upsertCommand);

        SearchTaxPeriodConfigUseCase.SearchTaxPeriodConfigCommand searchCommand = new SearchTaxPeriodConfigUseCase.SearchTaxPeriodConfigCommand(
                generateCommand.getTaxTypeCode(),
                generateCommand.getCalendarYear(),
                generateCommand.getTaxPeriodCode(),
                generateCommand.getTransactionTypeId()
        );

        SearchResult<RefTaxPeriodConfig> configPersisted = searchTaxPeriodConfigUseCase.search(searchCommand);
        validate(configPersisted);
        Assertions.assertEquals(recordId, configPersisted.getContent().get(0).getId());
    }

    private void validate(RefTaxPeriodConfig configLoaded) {
        configLoaded.getRecords().forEach(record -> {
            Assertions.assertNotNull(record.getPeriodName());
            Assertions.assertNotNull(record.getPeriodName().getTranslation("en"));
        });
    }

    private void validate(SearchResult<RefTaxPeriodConfig> configLoaded) {
        Assertions.assertNotNull(configLoaded);
        Assertions.assertNotNull(configLoaded.getContent());
        configLoaded.getContent().forEach(config -> {
            validate(config);
        });
    }

    @Test
    void loadTaxPeriods() {
        var taxPeriodTypesList = readTaxPeriodTypesUseCase.readAll();
        Assertions.assertNotNull(taxPeriodTypesList);
        taxPeriodTypesList.forEach(taxPeriod -> {
            var code = taxPeriod.getCode();
            var taxPeriodSegments =
                    loadTaxPeriodSegmentsUseCase.findByTaxPeriodId(taxPeriod.getId());
            Assertions.assertNotNull(taxPeriodSegments);
        });
    }
}
