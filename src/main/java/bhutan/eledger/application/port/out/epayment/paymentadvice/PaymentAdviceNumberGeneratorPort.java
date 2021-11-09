package bhutan.eledger.application.port.out.epayment.paymentadvice;

import java.time.LocalDate;

public interface PaymentAdviceNumberGeneratorPort {

    String generate(LocalDate localDate);
}
