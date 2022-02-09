package bhutan.eledger.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadTaxPeriodSegmentsUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import bhutan.eledger.domain.ref.taxperiodconfig.TaxPeriodRecord;
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
    void createTest() {
        LocalDate validFrom = LocalDate.now();
        LocalDate validTo = LocalDate.now();
        LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand generateCommand =
                new LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand(
                        GST_TAX_TYPE,
                        2022,
                        TaxPeriodType.MONTHLY.getValue(),
                        TRANSACTION_TYPE_LIABILITY,
                        11,
                        11,
                        validFrom,
                        validTo,
                        false);

        RefTaxPeriodConfig configGenerated = loadGenTaxPeriodConfigUseCase.loadGen(generateCommand);
        Assertions.assertNotNull(configGenerated);

        Collection<UpsertTaxPeriodUseCase.TaxPeriodRecordCommand> records = new LinkedList<>();
        for(TaxPeriodRecord gr : configGenerated.getRecords()) {
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
                        configGenerated.getTaxPeriodTypeCode(),
                        configGenerated.getTransactionTypeId(),
                        configGenerated.getDueDateCountForReturnFiling(),
                        configGenerated.getDueDateCountForPayment(),
                        configGenerated.getValidFrom(),
                        configGenerated.getValidTo(),
                        configGenerated.getConsiderNonWorkingDays(),
                        records
                );

        Long recordId = upsertTaxPeriodUseCase.upsert(upsertCommand);

        RefTaxPeriodConfig configLoaded = loadGenTaxPeriodConfigUseCase.loadGen(generateCommand);

//        try {
//            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(configLoaded));
//        } catch (Exception i) {}
        Assertions.assertNotNull(configLoaded);
        Assertions.assertEquals(recordId, configLoaded.getId());
    }

    @Test
    void loadTaxPeriods() {
        var taxPeriodTypesList = readTaxPeriodTypesUseCase.readAll();
        Assertions.assertNotNull(taxPeriodTypesList);
        taxPeriodTypesList.forEach(taxPeriod -> {
            var code = taxPeriod.getCode();
            var taxPeriodSegments =
                    loadTaxPeriodSegmentsUseCase.findByTaxPeriodTypeId(taxPeriod.getId());
            Assertions.assertNotNull(taxPeriodSegments);
        });
    }
}
