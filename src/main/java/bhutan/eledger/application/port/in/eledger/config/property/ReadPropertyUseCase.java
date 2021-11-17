package bhutan.eledger.application.port.in.eledger.config.property;

import bhutan.eledger.domain.eledger.config.property.Property;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadPropertyUseCase {

    Property readById(@NotNull Long id);

    Collection<Property> readAll();
}
