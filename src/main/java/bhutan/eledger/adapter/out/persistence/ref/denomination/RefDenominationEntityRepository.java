package bhutan.eledger.adapter.out.persistence.ref.denomination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RefDenominationEntityRepository extends JpaRepository<RefDenominationEntity, Long> {

    boolean existsByCode(String code);

    Optional<RefDenominationEntity> findByCode(String code);
}
