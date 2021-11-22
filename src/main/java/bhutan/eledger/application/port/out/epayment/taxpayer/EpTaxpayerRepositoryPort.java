package bhutan.eledger.application.port.out.epayment.taxpayer;

import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;

import java.util.Optional;

public interface EpTaxpayerRepositoryPort {

    EpTaxpayer create(EpTaxpayer taxpayer);

    Optional<EpTaxpayer> readById(Long id);

    Optional<EpTaxpayer> readByTpn(String tpn);

    void deleteAll();
}
