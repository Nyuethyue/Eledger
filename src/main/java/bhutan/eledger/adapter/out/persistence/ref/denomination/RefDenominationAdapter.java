package bhutan.eledger.adapter.out.persistence.ref.denomination;

import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.application.port.out.ref.denomination.RefDenominationRepositoryPort;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import bhutan.eledger.domain.ref.denomination.RefDenomination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefDenominationAdapter implements RefDenominationRepositoryPort {
    private final RefDenominationEntityRepository refDenominationEntityRepository;
    private final RefDenominationMapper refDenominationMapper;

    @Override
    public Long create(RefDenomination refDenomination) {
        RefDenominationEntity refDenominationEntity =
                refDenominationMapper.mapToEntity(refDenomination);

        return refDenominationEntityRepository.save(refDenominationEntity).getId();
    }

    @Override
    public Collection<RefDenomination> readAll() {
        return refDenominationEntityRepository.findAll()
                .stream()
                .map(refDenominationMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refDenominationEntityRepository.deleteAll();
    }

    @Override
    public Optional<RefDenomination> readById(Long id) {
        return refDenominationEntityRepository.findById(id)
                .map(refDenominationMapper::mapToDomain);
    }

    @Override
    public Optional<RefDenomination> readByCode(String code) {
        return refDenominationEntityRepository.findByCode(code)
                .map(refDenominationMapper::mapToDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return refDenominationEntityRepository.existsByCode(code);
    }

}
