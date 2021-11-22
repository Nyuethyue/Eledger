package bhutan.eledger.adapter.out.persistence.eledger.taxpayer;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.eledger.taxpayer.ElTaxpayer;

import java.util.Optional;

interface TaxpayerEntityRepository extends CustomQuerydslJpaRepository<ElTaxpayer, Long> {

    Optional<ElTaxpayer> findByTpn(String tpn);
}
