package bhutan.eledger.adapter.out.persistence.eledger.taxpayer;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.eledger.taxpayer.Taxpayer;

import java.util.Optional;

interface TaxpayerEntityRepository extends CustomQuerydslJpaRepository<Taxpayer, Long> {

    Optional<Taxpayer> findByTpn(String tpn);
}
