package bhutan.eledger.adapter.persistence.config.glaccount;

import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class GLAccountPartAdapter implements GLAccountPartRepositoryPort {
    private final GLAccountPartMapper glAccountPartMapper;
    private final GLAccountPartEntityRepository glAccountPartEntityRepository;

    @Override
    public Optional<GLAccountPart> readById(Long id) {
        return glAccountPartEntityRepository.findById(id)
                .map(glAccountPartMapper::mapToDomain);
    }

    @Override
    public List<GLAccountPart> readAllByIdInSortedByLevel(Collection<Long> ids) {
        return glAccountPartEntityRepository.queryAllByIdInSortedByLevel(ids)
                .stream()
                .map(glAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<GLAccountPart> readAllByParentId(Long parentId) {
        return glAccountPartEntityRepository.readAllByParentId(parentId)
                .stream()
                .map(glAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<GLAccountPart> readAll() {
        return glAccountPartEntityRepository.findAll()
                .stream()
                .map(glAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Long create(GLAccountPart glAccountPart) {
        GLAccountPartEntity partEntity = glAccountPartEntityRepository.save(
                glAccountPartMapper.mapToEntity(glAccountPart)
        );

        return partEntity.getId();
    }

    @Override
    public Collection<GLAccountPart> create(Collection<GLAccountPart> glAccountPart) {
        return glAccountPartEntityRepository.saveAll(
                        glAccountPart
                                .stream()
                                .map(glAccountPartMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(glAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existByParentIdAndCodeInList(Long parentId, Collection<String> codes) {
        return glAccountPartEntityRepository.existsByParentIdAndCodeIn(parentId, codes);
    }

    @Override
    public void deleteAll() {
        glAccountPartEntityRepository.deleteAll();
    }

    @Override
    public Collection<GLAccountPart> readAllByPartTypeId(Integer partTypeId) {
        return glAccountPartEntityRepository.readAllByGlAccountPartTypeId(partTypeId)
                .stream()
                .map(glAccountPartMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}
