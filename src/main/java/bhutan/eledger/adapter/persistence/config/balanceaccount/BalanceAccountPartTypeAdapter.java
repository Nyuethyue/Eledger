package bhutan.eledger.adapter.persistence.config.balanceaccount;

import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class BalanceAccountPartTypeAdapter implements BalanceAccountPartTypeRepositoryPort {

    private final BalanceAccountPartTypeMapper balanceAccountPartTypeMapper;
    private final BalanceAccountPartTypeEntityRepository balanceAccountPartTypeEntityRepository;

    @Override
    public Optional<BalanceAccountPartType> readById(Integer id) {
        return balanceAccountPartTypeEntityRepository.findById(id)
                .map(balanceAccountPartTypeMapper::mapToDomain);
    }

    @Override
    public Optional<BalanceAccountPartType> readByLevel(Integer level) {
        return balanceAccountPartTypeEntityRepository.findByLevel(level)
                .map(balanceAccountPartTypeMapper::mapToDomain);
    }

    @Override
    public Collection<BalanceAccountPartType> readAll() {
        return balanceAccountPartTypeEntityRepository.findAll()
                .stream()
                .map(balanceAccountPartTypeMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Integer create(BalanceAccountPartType balanceAccountPartType) {
        BalanceAccountPartTypeEntity balanceAccountPartTypeEntity =
                balanceAccountPartTypeMapper.mapToEntity(balanceAccountPartType);

        return balanceAccountPartTypeEntityRepository.save(balanceAccountPartTypeEntity).getId();
    }

    @Override
    public Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId) {
        return balanceAccountPartTypeEntityRepository.getIdOfNextPartType(fromPartTypeId);
    }

    @Override
    public boolean existById(Integer id) {
        return balanceAccountPartTypeEntityRepository.existsById(id);
    }

    @Override
    public boolean existByLevel(Integer level) {
        return balanceAccountPartTypeEntityRepository.existsByLevel(level);
    }

    @Override
    public void deleteById(Integer id) {
        balanceAccountPartTypeEntityRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        balanceAccountPartTypeEntityRepository.deleteAll();
    }
}
