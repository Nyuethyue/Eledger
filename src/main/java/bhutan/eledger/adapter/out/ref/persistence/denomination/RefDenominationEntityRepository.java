package bhutan.eledger.adapter.out.ref.persistence.denomination;

import org.springframework.data.jpa.repository.JpaRepository;


interface RefDenominationEntityRepository extends JpaRepository<RefDenominationEntity, Long> {
    boolean existsByValue(String value);
}
