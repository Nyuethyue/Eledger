package bhutan.eledger.application.port.out.eledger.config.datatype;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.eledger.config.datatype.DataType;

import java.util.Collection;
import java.util.Optional;

//todo cache
public interface DataTypeRepositoryPort {

    Collection<DataType> readAll();

    Optional<DataType> readById(Integer id);

    default DataType requiredReadById(Integer id) {
        return readById(id).orElseThrow(() ->
                new RecordNotFoundException("Data type by id: [" + id + "] not found.")
        );
    }

    boolean existsByType(String type);

    boolean existsById(Integer id);
}
