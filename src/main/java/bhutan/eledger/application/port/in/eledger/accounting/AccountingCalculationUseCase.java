package bhutan.eledger.application.port.in.eledger.accounting;

import java.time.LocalDate;

public interface AccountingCalculationUseCase {

    void calculate(LocalDate localDate);
}
