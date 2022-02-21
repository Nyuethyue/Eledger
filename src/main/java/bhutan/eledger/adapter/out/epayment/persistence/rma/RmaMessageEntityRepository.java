package bhutan.eledger.adapter.out.epayment.persistence.rma;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RmaMessageEntityRepository extends JpaRepository<RmaMessageEntity, Long> {

    Optional<RmaMessageEntity> findByOrderNo(String orderNo);
}
