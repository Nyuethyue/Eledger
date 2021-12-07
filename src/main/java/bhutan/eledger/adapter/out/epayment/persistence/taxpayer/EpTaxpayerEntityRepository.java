package bhutan.eledger.adapter.out.epayment.persistence.taxpayer;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;

import java.util.Optional;

interface EpTaxpayerEntityRepository extends CustomQuerydslJpaRepository<EpTaxpayer, Long> {

    Optional<EpTaxpayer> findByTpn(String tpn);
}
