package bhutan.eledger.application.port.out.eledger.accounting.formulation;

import java.time.LocalDate;

public interface FormulateAccountingPort {

    void formulate(String tpn, LocalDate localDate);
}
