package bhutan.eledger.application.port.out.epayment.taxpayer;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;

import java.util.Optional;

public interface EpTaxpayerRepositoryPort {

    Optional<EpTaxpayer> readById(String id);

    Optional<EpTaxpayer> readByTpn(String tpn);

    default EpTaxpayer requiredReadByTpn(String tpn) {
        return readByTpn(tpn)
                .orElseThrow(() -> new RecordNotFoundException("EpTaxpayer by tpn: [" + tpn + "] not found."));
    }
}
