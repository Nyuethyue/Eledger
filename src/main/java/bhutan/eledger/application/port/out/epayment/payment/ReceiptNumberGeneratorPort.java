package bhutan.eledger.application.port.out.epayment.payment;

import java.time.LocalDate;

public interface ReceiptNumberGeneratorPort {

    String generate(LocalDate localDate);
}
