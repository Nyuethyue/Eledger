package bhutan.eledger.application.port.out.config.datatype;

import bhutan.eledger.domain.config.datatype.DataType;

import java.util.Collection;
import java.util.Optional;

//todo cache
public interface DataTypeRepositoryPort {

    Collection<DataType> readAll();

    Optional<DataType> readById(Integer id);

    default DataType requiredReadById(Integer id) {
        return readById(id).orElseThrow();
    }

    boolean existsByType(String type);

    boolean existsById(Integer id);
}
