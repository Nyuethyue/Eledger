package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

interface RefTaxPeriodEntityRepository extends JpaRepository<RefTaxPeriodConfigEntity, Long> {

    boolean existsByCode(String code);

    Optional<RefTaxPeriodConfigEntity> findByCode(String code);

    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM ref.bank" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidity(String code, LocalDate date, LocalDate secondDate);

    @Query(value = "SELECT b.*" +
                   " FROM ref.bank b" +
                   " INNER JOIN ref.bank_branch bb" +
                   " ON b.id = bb. bank_id" +
                   " INNER JOIN ref.bank_account bacc" +
                   " ON bb.id = bacc.branch_id" +
                   " INNER JOIN ref.bank_account_gl_account_part bagl" +
                   " ON bacc.bank_account_gl_account_part_id = bagl.id" +
                   " WHERE bagl.code = :glPartFullCode" +
                   " AND ((b.end_of_validity IS NULL AND b.start_of_validity<=:currentDate)" +
                   " OR (:currentDate BETWEEN b.start_of_validity AND b.end_of_validity))" +
                   " AND ((bb.end_of_validity IS NULL AND bb.start_of_validity<=:currentDate)" +
                   " OR (:currentDate BETWEEN bb.start_of_validity AND bb.end_of_validity))" +
                   " AND ((bacc.end_of_validity IS NULL AND bacc.start_of_validity<=:currentDate)" +
                   " OR (:currentDate BETWEEN bacc.start_of_validity AND bacc.end_of_validity))", nativeQuery = true)
    Collection<RefTaxPeriodConfigEntity> getBankListByGlPartFullCode(String glPartFullCode, LocalDate currentDate);
}
