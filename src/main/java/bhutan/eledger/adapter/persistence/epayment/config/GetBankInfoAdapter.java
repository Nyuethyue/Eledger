package bhutan.eledger.adapter.persistence.epayment.config;

import am.iunetworks.lib.multilingual.core.Multilingual;
import am.iunetworks.lib.multilingual.core.Translation;
import bhutan.eledger.application.port.out.epayment.config.bank.GetBankInfoByGLCodeAccountPort;
import bhutan.eledger.domain.epayment.PaymentAdviceBankInfo;
import org.springframework.stereotype.Component;

@Component
class GetBankInfoAdapter implements GetBankInfoByGLCodeAccountPort {
    @Override
    public PaymentAdviceBankInfo getBankInfo(String glCode) {
        return PaymentAdviceBankInfo.withoutId(
                "999999999999",
                Multilingual.of(Translation.of(
                        "en",
                        "Test value"
                ))
        );
    }
}
