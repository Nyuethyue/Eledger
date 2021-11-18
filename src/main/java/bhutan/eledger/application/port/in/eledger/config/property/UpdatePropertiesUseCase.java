package bhutan.eledger.application.port.in.eledger.config.property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Validated
public interface UpdatePropertiesUseCase {

    Collection<Long> update(@Valid @NotNull UpdatePropertiesUseCase.UpdatePropertiesCommand command);

    @Data
    class UpdatePropertiesCommand {
        @NotNull
        @NotEmpty
        @Valid
        private final Collection<UpdatePropertyCommand> properties;
    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    class UpdatePropertyCommand extends UpdatePropertyUseCase.UpdatePropertyCommand {
        private final Long id;

        public UpdatePropertyCommand(Long id, LocalDate startOfValidity, String value, Map<String, String> descriptions) {
            super(startOfValidity, value, descriptions);
            this.id = id;
        }
    }

}
