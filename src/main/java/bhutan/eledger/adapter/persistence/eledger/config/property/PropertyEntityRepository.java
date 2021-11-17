package bhutan.eledger.adapter.persistence.eledger.config.property;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

interface PropertyEntityRepository extends JpaRepository<PropertyEntity, Long> {
    boolean existsByCode(String code);

    boolean existsByCodeIn(Collection<String> codes);
}
