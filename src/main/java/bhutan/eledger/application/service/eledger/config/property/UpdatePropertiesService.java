package bhutan.eledger.application.service.eledger.config.property;

import bhutan.eledger.application.port.in.eledger.config.property.UpdatePropertiesUseCase;
import bhutan.eledger.application.port.in.eledger.config.property.UpdatePropertyUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
class UpdatePropertiesService implements UpdatePropertiesUseCase {
    private final UpdatePropertyUseCase updatePropertyUseCase;

    @Override
    public Collection<Long> update(UpdatePropertiesCommand command) {
        return command.getProperties()
                .stream()
                .map(updatePropertyCommand ->
                        updatePropertyUseCase.update(updatePropertyCommand.getId(), updatePropertyCommand)
                )
                .collect(Collectors.toUnmodifiableList());
    }
}
