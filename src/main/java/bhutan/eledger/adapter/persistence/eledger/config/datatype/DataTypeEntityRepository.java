package bhutan.eledger.adapter.persistence.eledger.config.datatype;

import org.springframework.data.jpa.repository.JpaRepository;

interface DataTypeEntityRepository extends JpaRepository<DataTypeEntity, Integer> {
    boolean existsByType(String type);
}
