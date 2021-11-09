package bhutan.eledger.application.port.out.epayment.config.bank;

import bhutan.eledger.domain.epayment.PaymentAdviceBankInfo;

public interface GetBankInfoByGLCodeAccountPort {

    //todo tmp solution can be changed after bank config impl
    PaymentAdviceBankInfo getBankInfo(String glCode);

}
