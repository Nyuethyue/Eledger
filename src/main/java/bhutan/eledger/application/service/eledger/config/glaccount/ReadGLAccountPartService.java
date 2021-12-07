package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.glaccount.ReadGLAccountPartUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadGLAccountPartService implements ReadGLAccountPartUseCase {
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Override
    public GLAccountPart readById(Long id) {
        log.trace("Reading gl account part by id: {}", id);

        return glAccountPartRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("GLAccountPartType by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<GLAccountPart> readAllByParentId(Long parentId) {
        log.trace("Reading all gl account parts by parent id: {}", parentId);

        return glAccountPartRepositoryPort.readAllByParentId(parentId);
    }

    @Override
    public Collection<GLAccountPart> readAllByPartTypeId(Integer partTypeId) {
        log.trace("Reading all gl accounts by part type id.");

        return glAccountPartRepositoryPort.readAllByPartTypeId(partTypeId);
    }

    @Override
    public Boolean existsByFullCode(String fullCode) {
        log.trace("Checking whether gl account by full code.");

        return glAccountPartRepositoryPort.existsByFullCode(fullCode);
    }
}
