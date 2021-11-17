package bhutan.eledger.application.service.eledger.config.property;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.property.ReadPropertyUseCase;
import bhutan.eledger.application.port.out.eledger.config.property.PropertyRepositoryPort;
import bhutan.eledger.domain.eledger.config.property.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadPropertyService implements ReadPropertyUseCase {
    private final PropertyRepositoryPort propertyRepositoryPort;

    @Override
    public Property readById(Long id) {
        log.trace("Reading property by id: {}", id);

        return propertyRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Property by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<Property> readAll() {
        log.trace("Reading all properties.");

        return propertyRepositoryPort.readAll();
    }
}
