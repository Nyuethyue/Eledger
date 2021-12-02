package bhutan.eledger.application.port.in.eledger.config.property;

import bhutan.eledger.domain.eledger.config.property.Property;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreatePropertyUseCase {

    Collection<Property> create(@Valid CreatePropertyUseCase.CreatePropertiesCommand command);

    @Data
    class CreatePropertiesCommand {

        @JsonCreator
        public CreatePropertiesCommand(Collection<PropertyCommand> properties) {
            this.properties = properties;
        }

        @NotEmpty
        @Valid
        private final Collection<PropertyCommand> properties;
    }

    @Data
    class PropertyCommand {
        @NotNull
        private final String code;

        @NotNull
        private final String value;

        @NotNull
        @Future
        private final LocalDate startOfValidity;

        @NotEmpty
        private final Map<String, String> descriptions;

        @NotNull
        @Positive
        private final Integer dataTypeId;
    }
}
