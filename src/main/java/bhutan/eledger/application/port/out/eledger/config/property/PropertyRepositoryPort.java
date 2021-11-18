package bhutan.eledger.application.port.out.eledger.config.property;

import bhutan.eledger.domain.eledger.config.property.Property;

import java.util.Collection;
import java.util.Optional;

public interface PropertyRepositoryPort {

    Long create(Property property);

    Collection<Property> create(Collection<Property> properties);

    boolean isOpenPropertyExists(Property property);

    void deleteAll();

    Optional<Property> readById(Long id);

    Collection<Property> readAll();

    Property update(Property property);
}
