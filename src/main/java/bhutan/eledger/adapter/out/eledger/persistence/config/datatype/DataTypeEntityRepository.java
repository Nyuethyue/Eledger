package bhutan.eledger.adapter.out.eledger.persistence.config.datatype;

import org.springframework.data.jpa.repository.JpaRepository;

interface DataTypeEntityRepository extends JpaRepository<DataTypeEntity, Integer> {
    boolean existsByType(String type);
}
