package bhutan.eledger.application.port.out.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartType;

import java.util.Collection;
import java.util.Optional;

public interface GLAccountPartTypeRepositoryPort {

    Optional<GLAccountPartType> readById(Integer id);

    Optional<GLAccountPartType> readByLevel(Integer level);

    default GLAccountPartType requiredReadByLevel(Integer level) {
        return readByLevel(level)
                .orElseThrow(() -> new RecordNotFoundException("Part type by level:" + level + " not found."));
    }

    Collection<GLAccountPartType> readAll();

    Integer create(GLAccountPartType glAccountPartType);

    Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId);

    boolean existById(Integer id);

    boolean existByLevel(Integer level);

    void deleteById(Integer id);

    void deleteAll();
}
