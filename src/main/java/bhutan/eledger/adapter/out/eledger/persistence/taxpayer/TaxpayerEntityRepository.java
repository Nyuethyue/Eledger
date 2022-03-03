package bhutan.eledger.adapter.out.eledger.persistence.taxpayer;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.eledger.taxpayer.ElTaxpayer;
import org.springframework.data.jpa.repository.query.Procedure;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

interface TaxpayerEntityRepository extends CustomQuerydslJpaRepository<ElTaxpayer, Long> {

    Optional<ElTaxpayer> findByTpn(String tpn);

    @Procedure(procedureName = "eledger.fn_get_net_negative_balance")
    BigDecimal getNetNegativeBalance(String tpn, LocalDate calculateDate);
}
