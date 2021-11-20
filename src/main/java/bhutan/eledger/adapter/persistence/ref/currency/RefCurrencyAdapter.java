package bhutan.eledger.adapter.persistence.ref.currency;

import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefCurrencyAdapter implements RefCurrencyRepositoryPort {
    private final RefCurrencyEntityRepository refCurrencyEntityRepository;
    private final RefCurrencyMapper refCurrencyMapper;

    @Override
    public Long create(RefCurrency refCurrency) {
        RefCurrencyEntity refCurrencyEntity =
                refCurrencyMapper.mapToEntity(refCurrency);

        return refCurrencyEntityRepository.save(refCurrencyEntity).getId();
    }

    @Override
    public Collection<RefCurrency> readAll() {
        return refCurrencyEntityRepository.findAll()
                .stream()
                .map(refCurrencyMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refCurrencyEntityRepository.deleteAll();
    }

    @Override
    public Optional<RefCurrency> readById(Long id) {
        return refCurrencyEntityRepository.findById(id)
                .map(refCurrencyMapper::mapToDomain);
    }

    @Override
    public Optional<RefCurrency> readByCode(String code) {
        return refCurrencyEntityRepository.findByCode(code)
                .map(refCurrencyMapper::mapToDomain);
    }

}
