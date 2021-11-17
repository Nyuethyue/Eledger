package bhutan.eledger.application.service.eledger.config.property;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.property.CreatePropertyUseCase;
import bhutan.eledger.application.port.out.eledger.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.property.PropertyRepositoryPort;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import bhutan.eledger.domain.eledger.config.property.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreatePropertyService implements CreatePropertyUseCase {
    private final PropertyRepositoryPort propertyRepositoryPort;
    private final DataTypeRepositoryPort dataTypeRepositoryPort;

    @Override
    public Collection<Property> create(@Valid CreatePropertiesCommand command) {
        log.trace("Creating property with command: {}", command);

        var properties = commandToProperties(command);

        checkPropertyExistenceByCode(properties);

        log.trace("Persisting properties: {}", properties);

        var persistedProperties = propertyRepositoryPort.create(properties);

        log.trace("Properties: {} successfully created.", persistedProperties);

        return persistedProperties;
    }

    private void checkPropertyExistenceByCode(Collection<Property> properties) {
        var propertyCodes = properties
                .stream()
                .map(Property::getCode)
                .collect(Collectors.toUnmodifiableList());

        if (propertyRepositoryPort.existsByAnyCode(propertyCodes)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("properties", "Property with one of these codes: [" + propertyCodes + "] already exists.")
            );
        }
    }

    private Collection<Property> commandToProperties(CreatePropertiesCommand command) {
        return command.getProperties()
                .stream()
                .map(this::commandToProperty)
                .collect(Collectors.toUnmodifiableList());
    }

    private Property commandToProperty(PropertyCommand command) {
        DataType dataType = dataTypeRepositoryPort.readById(command.getDataTypeId())
                .orElseThrow(() -> new ViolationException(
                                new ValidationError()
                                        .addViolation("dataTypeId", "Data type with id: [" + command.getDataTypeId() + "] doesn't exists.")
                        )
                );

        return Property.withoutId(
                command.getCode(),
                dataType,
                Multilingual.fromMap(command.getDescriptions())
        );
    }
}
