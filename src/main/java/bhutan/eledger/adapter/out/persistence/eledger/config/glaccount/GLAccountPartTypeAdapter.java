package bhutan.eledger.adapter.out.persistence.eledger.config.glaccount;

import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class GLAccountPartTypeAdapter implements GLAccountPartTypeRepositoryPort {

    private final GLAccountPartTypeMapper glAccountPartTypeMapper;
    private final GLAccountPartTypeEntityRepository glAccountPartTypeEntityRepository;

    @Override
    public Optional<GLAccountPartType> readById(Integer id) {
        return glAccountPartTypeEntityRepository.findById(id)
                .map(glAccountPartTypeMapper::mapToDomain);
    }

    @Override
    public Optional<GLAccountPartType> readByLevel(Integer level) {
        return glAccountPartTypeEntityRepository.findByLevel(level)
                .map(glAccountPartTypeMapper::mapToDomain);
    }

    @Override
    public Collection<GLAccountPartType> readAll() {
        return glAccountPartTypeEntityRepository.findAll()
                .stream()
                .map(glAccountPartTypeMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Integer create(GLAccountPartType glAccountPartType) {
        GLAccountPartTypeEntity glAccountPartTypeEntity =
                glAccountPartTypeMapper.mapToEntity(glAccountPartType);

        return glAccountPartTypeEntityRepository.save(glAccountPartTypeEntity).getId();
    }

    @Override
    public Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId) {
        return glAccountPartTypeEntityRepository.getIdOfNextPartType(fromPartTypeId);
    }

    @Override
    public boolean existById(Integer id) {
        return glAccountPartTypeEntityRepository.existsById(id);
    }

    @Override
    public boolean existByLevel(Integer level) {
        return glAccountPartTypeEntityRepository.existsByLevel(level);
    }

    @Override
    public void deleteById(Integer id) {
        glAccountPartTypeEntityRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        glAccountPartTypeEntityRepository.deleteAll();
    }
}
