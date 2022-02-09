package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


interface RefTaxPeriodTypeEntityRepository extends JpaRepository<RefTaxPeriodType, Long> {
    Optional<RefTaxPeriodType> findByCode(String code);
}
