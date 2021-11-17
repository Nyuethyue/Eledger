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
public class RefBankAdapter implements RefBankRepositoryPort {

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
    public Optional<RefBank> readByBankName(String bankName) {
        return refBankEntityRepository.findByBankName(bankName)
                .map(refBankMapper::mapToDomain);
    }

    @Override
    public Optional<RefBank> readByBfscCode(String bfscCode) {
        return refBankEntityRepository.findByBfscCode(bfscCode)
                .map(refBankMapper::mapToDomain);
    }

    @Override
    public boolean existByBfscCode(String bfscCode) {
        return refBankEntityRepository.existsByBfscCode(bfscCode);
    }
}
