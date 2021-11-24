package bhutan.eledger.application.port.out.epayment.generatereceipt;

import java.time.LocalDate;

public interface ReceiptNumberGeneratorPort {

    String generate(LocalDate localDate);
}
