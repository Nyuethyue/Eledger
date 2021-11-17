package bhutan.eledger.adapter.persistence.ref.bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RefBankEntityRepository extends JpaRepository<RefBankEntity, Long> {

    boolean existsByBfscCode(String bfscCode);

    Optional<RefBankEntity> findByBankName(String bankName);

    Optional<RefBankEntity> findByBfscCode(String bfscCode);


}
