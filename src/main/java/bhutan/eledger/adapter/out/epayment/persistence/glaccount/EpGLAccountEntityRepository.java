package bhutan.eledger.adapter.out.epayment.persistence.glaccount;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;

import java.util.Optional;

interface EpGLAccountEntityRepository extends CustomQuerydslJpaRepository<EpGLAccount, Long> {

    Optional<EpGLAccount> findByCode(String code);
}
