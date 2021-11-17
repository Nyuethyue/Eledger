package bhutan.eledger.adapter.persistence.eledger.config.property;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import bhutan.eledger.domain.eledger.config.property.Property;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class PropertyMapper {

    PropertyEntity mapToEntity(Property property) {

        var propertyEntity = new PropertyEntity(
                property.getId(),
                property.getCode(),
                property.getDataType().getId()
        );

        propertyEntity.setDescriptions(
                property.getDescription()
                        .getTranslations()
                        .stream()
                        .map(t ->
                                new PropertyDescriptionEntity(
                                        t.getId(),
                                        t.getLanguageCode(),
                                        t.getValue(),
                                        propertyEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return propertyEntity;
    }

    Property mapToDomain(PropertyEntity propertyEntity, DataType dataType) {
        return Property.withId(
                propertyEntity.getId(),
                propertyEntity.getCode(),
                dataType,
                Multilingual.of(propertyEntity.getDescriptions())
        );
    }
}
