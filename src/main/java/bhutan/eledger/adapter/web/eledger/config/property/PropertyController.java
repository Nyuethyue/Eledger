package bhutan.eledger.adapter.web.eledger.config.property;

import bhutan.eledger.application.port.in.eledger.config.property.CreatePropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.ReadPropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.UpdatePropertiesUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.UpdatePropertyUseCase;
import bhutan.eledger.domain.eledger.config.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/config/property")
@RequiredArgsConstructor
class PropertyController {

    private final CreatePropertyUseCase createPropertyUseCase;
    private final ReadPropertyUseCase readPropertyUseCase;
    private final UpdatePropertiesUseCase updatePropertiesUseCase;
    private final UpdatePropertyUseCase updatePropertyUseCase;

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdatePropertyUseCase.UpdatePropertyCommand command) {
        Long newPropertyId = updatePropertyUseCase.update(id, command);

        return ResponseEntity
                .created(URI.create("/" + newPropertyId))
                .build();
    }

    @PutMapping
    public ResponseEntity<Collection<Long>> updateAll(@RequestBody UpdatePropertiesUseCase.UpdatePropertiesCommand command) {
        Collection<Long> newPropertyIds = updatePropertiesUseCase.update(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPropertyIds);
    }
}
