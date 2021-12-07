package bhutan.eledger.adapter.in.eledger.web.config.datatype;

import bhutan.eledger.application.port.in.eledger.config.datatype.ReadDataTypeUseCase;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/datatype")
@RequiredArgsConstructor
class DataTypeController {
    private final ReadDataTypeUseCase readDataTypeUseCase;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public DataType getById(@PathVariable Integer id) {
        return readDataTypeUseCase.readById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<DataType> getAll() {
        return readDataTypeUseCase.readAll();
    }
}
