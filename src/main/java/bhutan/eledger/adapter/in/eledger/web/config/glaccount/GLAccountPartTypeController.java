package bhutan.eledger.adapter.in.eledger.web.config.glaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.ReadGLAccountPartTypeUseCase;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPartType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/config/gl/account/part/type")
@RequiredArgsConstructor
class GLAccountPartTypeController {
    private final CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;
    private final ReadGLAccountPartTypeUseCase readGLAccountPartTypeUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand command) {
        Integer id = createGLAccountPartTypeUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public GLAccountPartType getById(@PathVariable Integer id) {
        return readGLAccountPartTypeUseCase.readById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<GLAccountPartType> getAll() {
        return readGLAccountPartTypeUseCase.readAll();
    }
}
