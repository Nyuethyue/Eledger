package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class GLAccountAdapter implements GLAccountRepositoryPort {
    private final GLAccountMapper glAccountMapper;
    private final GLAccountEntityRepository glAccountEntityRepository;

    @Override
    public Optional<GLAccount> readById(Long id) {
        return glAccountEntityRepository.findById(id)
                .map(glAccountMapper::mapToDomain);
    }

    @Override
    public Collection<GLAccount> readAll() {
        return glAccountEntityRepository.findAll()
                .stream()
                .map(glAccountMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Long create(GLAccount glAccount) {
        GLAccountEntity glAccountEntity = glAccountEntityRepository.save(
                glAccountMapper.mapToEntity(glAccount)
        );

        return glAccountEntity.getId();
    }

    @Override
    public void update(GLAccount glAccount) {
        glAccountEntityRepository.save(
                glAccountMapper.mapToEntity(glAccount)
        );
    }

    @Override
    public void deleteAll() {
        glAccountEntityRepository.deleteAll();
    }

    @Override
    public boolean existsByCode(Collection<String> codes) {

        return glAccountEntityRepository.existsByCodeIn(codes);
    }
}
