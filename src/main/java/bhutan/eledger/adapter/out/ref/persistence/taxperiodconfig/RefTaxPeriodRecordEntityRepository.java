package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

interface RefTaxPeriodRecordEntityRepository extends JpaRepository<RefTaxPeriodRecordEntity, Long> {

    @Query(value = "SELECT *" +
            " FROM ref.ref_tax_period_record tpr" +
            " WHERE tpr.tax_period_config_id = :taxPeriodConfigId"
            , nativeQuery = true)
    Collection<RefTaxPeriodRecordEntity> readRecords(long taxPeriodConfigId);

    void deleteByTaxPeriodConfigId(Long taxPeriodConfigId);
}
