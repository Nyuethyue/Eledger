package bhutan.eledger.adapter.persistence.ref.paymentmode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface PaymentModeEntityRepository extends JpaRepository<PaymentModeEntity, Long> {
    Optional<PaymentModeEntity> findByCode(String code);
}
