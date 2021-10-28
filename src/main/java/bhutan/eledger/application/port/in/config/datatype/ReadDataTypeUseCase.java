package bhutan.eledger.application.port.in.config.datatype;

import bhutan.eledger.domain.config.datatype.DataType;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface ReadDataTypeUseCase {

    DataType readById(@NotNull Integer id);

    Collection<DataType> readAll();
}
