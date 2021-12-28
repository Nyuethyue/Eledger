package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;

import java.util.Optional;

public interface ReconciliationUploadRecordEntityRepository extends CustomQuerydslJpaRepository<DepositEntity, Long> {
    Optional<DepositEntity> readByDepositNumber(String depositNumber);
}
