package bhutan.eledger.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchDepositTest {
    @Autowired
    private SearchDepositUseCase searchDepositUseCase;

    @BeforeEach
    void beforeEach() {}


    @AfterEach
    void afterEach() {
    }


    @Test
    void searchTest() {
        var searchResult = searchDepositUseCase.search(new SearchDepositUseCase.SearchDepositCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(10)
        ));
        Assertions.assertTrue(searchResult.getTotalCount() == 0);
    }
}
