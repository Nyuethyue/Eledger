package bhutan.eledger.adapter.out.epayment.persistence.rma;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RmaMessageResponseEntityRepository extends JpaRepository<RmaMessageResponseEntity, Long> {

    Optional<RmaMessageResponseEntity> findTopByOrderNoOrderByCreationDateTimeDesc(String orderNo);
}
