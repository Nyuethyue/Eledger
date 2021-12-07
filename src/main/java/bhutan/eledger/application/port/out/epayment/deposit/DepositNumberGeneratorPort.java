package bhutan.eledger.application.port.out.epayment.deposit;

import java.time.LocalDate;

public interface DepositNumberGeneratorPort {

    String generate(LocalDate localDate);
}
