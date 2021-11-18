package bhutan.eledger.adapter.persistence.ref.bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RefBankEntityRepository extends JpaRepository<RefBankEntity, Long> {

    boolean existsByCode(String code);

    Optional<RefBankEntity> findByCode(String code);


}
