package bhutan.eledger.application.port.in.eledger.config.datatype;

import bhutan.eledger.domain.eledger.config.datatype.DataType;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface ReadDataTypeUseCase {

    DataType readById(@NotNull Integer id);

    Collection<DataType> readAll();
}
