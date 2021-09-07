package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class BalanceAccountAdapter implements BalanceAccountRepositoryPort {
    private final BalanceAccountMapper balanceAccountMapper;
    private final BalanceAccountEntityRepository balanceAccountEntityRepository;

    @Override
    public Optional<BalanceAccount> readById(Long id) {
        return balanceAccountEntityRepository.findById(id)
                .map(balanceAccountMapper::mapToDomain);
    }

    @Override
    public Collection<BalanceAccount> readAll() {
        return balanceAccountEntityRepository.findAll()
                .stream()
                .map(balanceAccountMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Long create(BalanceAccount balanceAccount) {
        BalanceAccountEntity balanceAccountEntity = balanceAccountEntityRepository.save(
                balanceAccountMapper.mapToEntity(balanceAccount)
        );

        return balanceAccountEntity.getId();
    }

    @Override
    public void update(BalanceAccount balanceAccount) {
        balanceAccountEntityRepository.save(
                balanceAccountMapper.mapToEntity(balanceAccount)
        );
    }

    @Override
    public void deleteAll() {
        balanceAccountEntityRepository.deleteAll();
    }
}
