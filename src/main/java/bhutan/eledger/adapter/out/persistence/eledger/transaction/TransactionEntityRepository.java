package bhutan.eledger.adapter.out.persistence.eledger.transaction;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;

interface TransactionEntityRepository extends CustomQuerydslJpaRepository<TransactionEntity, Long> {

}
