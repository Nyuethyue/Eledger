package bhutan.eledger.adapter.persistence.ref.bank;


import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefBankAdapter implements RefBankRepositoryPort {

    private final RefBankMapper refBankMapper;
    private final RefBankEntityRepository refBankEntityRepository;


    @Override
    public Long create(RefBank refBank) {
        RefBankEntity refBankEntity =
                refBankMapper.mapToEntity(refBank);

        return refBankEntityRepository.save(refBankEntity).getId();
    }

    @Override
    public Collection<RefBank> readAll() {
        return refBankEntityRepository.findAll()
                .stream()
                .map(refBankMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refBankEntityRepository.deleteAll();
    }

    @Override
    public Optional<RefBank> readById(Long id) {
        return refBankEntityRepository.findById(id)
                .map(refBankMapper::mapToDomain);
    }


    @Override
    public Optional<RefBank> readByCode(String code) {
        return refBankEntityRepository.findByCode(code)
                .map(refBankMapper::mapToDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return refBankEntityRepository.existsByCode(code);
    }

    @Override
    public boolean existsById(Long id) {
        return refBankEntityRepository.existsById(id);
    }
}
