package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


interface RefTaxPeriodTypeEntityRepository extends JpaRepository<RefTaxPeriod, Long> {
    Optional<RefTaxPeriod> findByCode(String code);
}
