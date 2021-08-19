package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class BalanceAccountPartAdapter implements BalanceAccountPartRepositoryPort {
    private final BalanceAccountPartMapper balanceAccountPartMapper;
    private final BalanceAccountPartEntityRepository balanceAccountPartEntityRepository;

    @Override
    public Optional<BalanceAccountPart> readById(Long id) {
        return balanceAccountPartEntityRepository.findById(id)
                .map(balanceAccountPartMapper::mapToDomain);
    }

    @Override
    public Collection<BalanceAccountPart> readAll() {
        return balanceAccountPartEntityRepository.findAll()
                .stream()
                .map(balanceAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Long create(BalanceAccountPart balanceAccountPart) {
        BalanceAccountPartEntity partEntity = balanceAccountPartEntityRepository.save(
                balanceAccountPartMapper.mapToEntity(balanceAccountPart)
        );

        return partEntity.getId();
    }

    @Override
    public Collection<BalanceAccountPart> create(Collection<BalanceAccountPart> balanceAccountParts) {
        return balanceAccountPartEntityRepository.saveAll(
                        balanceAccountParts
                                .stream()
                                .map(balanceAccountPartMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(balanceAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existByParentIdAndCodeInList(Long parentId, Collection<String> codes) {
        return balanceAccountPartEntityRepository.existsByParentIdAndCodeIn(parentId, codes);
    }
}
