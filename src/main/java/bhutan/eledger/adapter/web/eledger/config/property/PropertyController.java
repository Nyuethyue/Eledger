package bhutan.eledger.adapter.web.eledger.config.property;

import bhutan.eledger.application.port.in.eledger.config.property.CreatePropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.ReadPropertyUseCase;
import bhutan.eledger.domain.eledger.config.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/property")
@RequiredArgsConstructor
class PropertyController {

    private final CreatePropertyUseCase createPropertyUseCase;
    private final ReadPropertyUseCase readPropertyUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreatePropertyUseCase.CreatePropertiesCommand command) {
        var properties = createPropertyUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(properties);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Property getById(@PathVariable Long id) {
        return readPropertyUseCase.readById(id);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<Property> getAll() {
        return readPropertyUseCase.readAll();
    }
}
