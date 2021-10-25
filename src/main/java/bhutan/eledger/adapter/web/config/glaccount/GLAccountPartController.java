package bhutan.eledger.adapter.web.config.glaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.config.glaccount.ReadGLAccountPartUseCase;
import bhutan.eledger.application.port.in.config.glaccount.SearchGLAccountPartUseCase;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/gl/account/part")
@RequiredArgsConstructor
class GLAccountPartController {
    private final CreateGLAccountPartUseCase createGLAccountPartUseCase;
    private final ReadGLAccountPartUseCase readGLAccountPartUseCase;
    private final SearchGLAccountPartUseCase searchGLAccountPartUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateGLAccountPartUseCase.CreateGLAccountPartCommand command) {
        var glAccountParts =
                createGLAccountPartUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(glAccountParts);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public GLAccountPart getById(@PathVariable Long id) {
        return readGLAccountPartUseCase.readById(id);
    }

    @GetMapping(value = "/allByPartType/{partTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<GLAccountPart> getAllByPartTypeId(@PathVariable Integer partTypeId) {
        return readGLAccountPartUseCase.readAllByPartTypeId(partTypeId);
    }

    @GetMapping(value = "/children/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<GLAccountPart> getAllByParentId(@PathVariable Long parentId) {
        return readGLAccountPartUseCase.readAllByParentId(parentId);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<GLAccountPart> search(@RequestBody SearchGLAccountPartUseCase.SearchGLAccountPartCommand command) {
        return searchGLAccountPartUseCase.search(command);
    }
}
