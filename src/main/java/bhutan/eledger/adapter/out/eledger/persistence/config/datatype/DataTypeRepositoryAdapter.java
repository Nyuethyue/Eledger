package bhutan.eledger.adapter.out.eledger.persistence.config.datatype;

import bhutan.eledger.application.port.out.eledger.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DataTypeRepositoryAdapter implements DataTypeRepositoryPort {

    private final DataTypeEntityRepository dataTypeEntityRepository;

    @Override
    public Collection<DataType> readAll() {
        return dataTypeEntityRepository.findAll()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<DataType> readById(Integer id) {
        var x = dataTypeEntityRepository.findById(id);
        return dataTypeEntityRepository.findById(id)
                .map(DataType.class::cast);
    }

    @Override
    public boolean existsByType(String type) {
        return dataTypeEntityRepository.existsByType(type);
    }

    @Override
    public boolean existsById(Integer id) {
        return dataTypeEntityRepository.existsById(id);
    }
}
