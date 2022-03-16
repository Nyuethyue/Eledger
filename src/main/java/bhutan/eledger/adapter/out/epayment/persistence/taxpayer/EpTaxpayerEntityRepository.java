package bhutan.eledger.adapter.out.epayment.persistence.taxpayer;

import am.iunetworks.lib.common.persistence.spring.repository.ReadOnlyRepository;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;

import java.util.Optional;

interface EpTaxpayerEntityRepository extends ReadOnlyRepository<EpTaxpayer, String> {

    Optional<EpTaxpayer> findByTpn(String tpn);
}
