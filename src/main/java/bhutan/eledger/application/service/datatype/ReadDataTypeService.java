package bhutan.eledger.application.service.datatype;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.config.datatype.ReadDataTypeUseCase;
import bhutan.eledger.application.port.in.config.glaccount.ReadGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.datatype.DataType;
import bhutan.eledger.domain.config.glaccount.GLAccountPartType;
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
