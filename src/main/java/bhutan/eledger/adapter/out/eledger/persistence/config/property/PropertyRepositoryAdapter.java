package bhutan.eledger.adapter.out.eledger.persistence.config.property;

import bhutan.eledger.application.port.out.eledger.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.property.PropertyRepositoryPort;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import bhutan.eledger.domain.eledger.config.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PropertyRepositoryAdapter implements PropertyRepositoryPort {

    private final PropertyEntityRepository propertyEntityRepository;
    private final PropertyMapper propertyMapper;
    private final DataTypeRepositoryPort dataTypeRepositoryPort;

    @Override
    public Long create(Property property) {
        var propertyEntity = propertyEntityRepository.save(
                propertyMapper.mapToEntity(property)
        );

        return propertyEntity.getId();
    }

    @Override
    public Collection<Property> create(Collection<Property> properties) {
        return propertyEntityRepository.saveAll(
                        properties
                                .stream()
                                .map(propertyMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(entity -> {
                    DataType dataType = dataTypeRepositoryPort.requiredReadById(entity.getDataTypeId());
                    return propertyMapper.mapToDomain(entity, dataType);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isOpenPropertyExists(Property property) {
        return propertyEntityRepository.existsByCodeAndEndOfValidityNullOrEndOfValidityGeAndEndOfValidityGe(property.getCode(), LocalDate.now(), property.getValidityPeriod().getStart());
    }

    @Override
    public void deleteAll() {
        propertyEntityRepository.deleteAll();
    }

    @Override
    public Optional<Property> readById(Long id) {
        return propertyEntityRepository.findById(id)
                .map(entity -> {
                    DataType dataType = dataTypeRepositoryPort.requiredReadById(entity.getDataTypeId());
                    return propertyMapper.mapToDomain(entity, dataType);
                });
    }

    @Override
    public Collection<Property> readAll() {
        return propertyEntityRepository.findAll()
                .stream()
                .map(entity -> {
                    DataType dataType = dataTypeRepositoryPort.requiredReadById(entity.getDataTypeId());
                    return propertyMapper.mapToDomain(entity, dataType);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Property update(Property property) {
        PropertyEntity propertyEntity = propertyMapper.mapToEntity(property);

        var updatedPropertyEntity = propertyEntityRepository.save(propertyEntity);

        return propertyMapper.mapToDomain(updatedPropertyEntity, property.getDataType());
    }
}
