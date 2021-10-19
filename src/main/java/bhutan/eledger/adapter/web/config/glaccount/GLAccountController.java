package bhutan.eledger.adapter.web.config.glaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountUseCase;
import bhutan.eledger.application.port.in.config.glaccount.SearchGLAccountUseCase;
import bhutan.eledger.application.port.in.config.glaccount.UpdateGLAccountUseCase;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/config/gl/account")
@RequiredArgsConstructor
class GLAccountController {
    private final CreateGLAccountUseCase createGLAccountUseCase;
    private final SearchGLAccountUseCase searchGLAccountUseCase;
    private final UpdateGLAccountUseCase updateGLAccountUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateGLAccountUseCase.CreateGLAccountCommand command) {
        Long id = createGLAccountUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateGLAccountUseCase.UpdateGLAccountCommand command) {
        updateGLAccountUseCase.updateGLAccount(id, command);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<GLAccount> search(@RequestBody SearchGLAccountUseCase.SearchGLAccountCommand command) {
        return searchGLAccountUseCase.search(command);
    }
}
