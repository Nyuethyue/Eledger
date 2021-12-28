package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;

import java.util.Optional;

public interface ReconciliationUploadFileEntityRepository extends CustomQuerydslJpaRepository<ReconciliationUploadFileEntity, Long> {
}
