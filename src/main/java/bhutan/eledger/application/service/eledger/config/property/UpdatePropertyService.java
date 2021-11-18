package bhutan.eledger.application.service.eledger.config.property;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.property.ReadPropertyUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.UpdatePropertyUseCase;
import bhutan.eledger.application.port.out.eledger.config.property.PropertyRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.eledger.config.property.Property;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
class UpdatePropertyService implements UpdatePropertyUseCase {
    private final ReadPropertyUseCase readPropertyUseCase;
    private final PropertyRepositoryPort propertyRepositoryPort;

    @Override
    @NonNull
    public Long update(Long id, UpdatePropertyUseCase.UpdatePropertyCommand command) {
        log.trace("Updating property by id: {} with command: {}", id, command);

        Property existedProperty = readPropertyUseCase.readById(id);

        //todo if needed check is something updated.

        Property updatedProperty = propertyRepositoryPort.update(
                existedProperty.toBuilder()
                        .validityPeriod(existedProperty.getValidityPeriod().end(command.getStartOfValidity()))
                        .build()
        );

        log.debug("Existed property successfully closed. Id: {}", id);

        Property newProperty = Property.withoutId(
                updatedProperty.getCode(),
                existedProperty.getDataType(),
                command.getValue(),
                ValidityPeriod.withOnlyStartOfValidity(
                        updatedProperty.getValidityPeriod().getEnd()
                ),

                Multilingual.fromMapOrEmpty(command.getDescriptions())
        );

        log.trace("Updated existed property: {}, new property will be created: {}", updatedProperty, newProperty);

        Long newPropertyId = propertyRepositoryPort.create(newProperty);

        log.debug("New property successfully created. Id: {}", newPropertyId);

        return newPropertyId;
    }
}
