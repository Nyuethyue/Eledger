package bhutan.eledger.domain.epayment.payment;

import java.time.LocalDate;

public interface FlatReceipt {
    Long getId();
    String getReceiptNumber();
    String getInstrumentNumber();
    LocalDate getInstrumentDate();
}
