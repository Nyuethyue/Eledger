package bhutan.eledger.adapter.in.ref.web.agency;

import bhutan.eledger.application.port.in.ref.agency.CreateRefAgencyUseCase;
import bhutan.eledger.application.port.in.ref.agency.ReadRefAgencyUseCase;
import bhutan.eledger.domain.ref.agency.RefAgency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/agency")
@RequiredArgsConstructor
class RefAgencyController {

    private final CreateRefAgencyUseCase createRefAgencyUseCase;
    private final ReadRefAgencyUseCase readRefAgencyUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefAgencyUseCase.CreateRefAgencyCommand command) {
        Long id = createRefAgencyUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefAgency> getAll() {
        return readRefAgencyUseCase.readAll();
    }
}
