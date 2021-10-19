package bhutan.eledger.application.port.out.config.glaccount;

import bhutan.eledger.domain.config.glaccount.GLAccountPartType;

import java.util.Collection;
import java.util.Optional;

public interface GLAccountPartTypeRepositoryPort {

    Optional<GLAccountPartType> readById(Integer id);

    Optional<GLAccountPartType> readByLevel(Integer level);

    Collection<GLAccountPartType> readAll();

    Integer create(GLAccountPartType glAccountPartType);

    Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId);

    boolean existById(Integer id);

    boolean existByLevel(Integer level);

    void deleteById(Integer id);

    void deleteAll();
}
