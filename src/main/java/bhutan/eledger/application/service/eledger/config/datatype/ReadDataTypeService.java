package bhutan.eledger.application.service.eledger.config.datatype;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.datatype.ReadDataTypeUseCase;
import bhutan.eledger.application.port.out.eledger.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadDataTypeService implements ReadDataTypeUseCase {
    private final DataTypeRepositoryPort dataTypeRepositoryPort;

    @Override
    public DataType readById(Integer id) {
        log.trace("Reading data type by id: {}", id);

        return dataTypeRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("GLAccountPartType by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<DataType> readAll() {
        log.trace("Reading all data types.");

        return dataTypeRepositoryPort.readAll();
    }
}
