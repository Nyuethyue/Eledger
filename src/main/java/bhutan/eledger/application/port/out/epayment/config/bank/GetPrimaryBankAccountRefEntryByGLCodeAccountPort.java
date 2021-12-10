package bhutan.eledger.application.port.out.epayment.config.bank;

import bhutan.eledger.common.ref.refentry.RefEntry;

import java.time.LocalDate;

public interface GetPrimaryBankAccountRefEntryByGLCodeAccountPort {

    RefEntry getPrimaryBankAccountByGLCode(String glCode, LocalDate validityDate);
}
