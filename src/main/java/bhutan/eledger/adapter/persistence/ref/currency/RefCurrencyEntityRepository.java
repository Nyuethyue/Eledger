package bhutan.eledger.adapter.persistence.ref.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RefCurrencyEntityRepository extends JpaRepository<RefCurrencyEntity, Long> {

    boolean existsByCode(String code);

    Optional<RefCurrencyEntity> findByCode(String code);
}
