package bhutan.eledger.application.port.out.config.glaccount;

import bhutan.eledger.domain.config.glaccount.GLAccountPart;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GLAccountPartRepositoryPort {

    Optional<GLAccountPart> readById(Long id);

    List<GLAccountPart> readAllByIdInSortedByLevel(Collection<Long> ids);

    Collection<GLAccountPart> readAllByParentId(Long parentId);

    Collection<GLAccountPart> readAll();

    Long create(GLAccountPart glAccountPart);

    Collection<GLAccountPart> create(Collection<GLAccountPart> glAccountPart);

    boolean existByParentIdAndCodeInList(Long parentId, Collection<String> codes);

    void deleteAll();
}
