package bhutan.eledger.adapter.out.eledger.persistence.transaction;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;

interface TransactionEntityRepository extends CustomQuerydslJpaRepository<TransactionEntity, Long> {

}
