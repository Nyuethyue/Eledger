package bhutan.eledger.adapter.out.ref.persistence.agencyglaccount;

import bhutan.eledger.application.port.out.ref.agencyglaccount.RefAgencyGLAccountRepositoryPort;
import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefAgencyGLAccountAdapter implements RefAgencyGLAccountRepositoryPort {
    private final RefAgencyGLAccountMapper refAgencyGLAccountMapper;
    private final RefAgencyGLAccountEntityRepository refAgencyGLAccountEntityRepository;

    @Override
    public Collection<RefAgencyGLAccount> create(Collection<RefAgencyGLAccount> agencyGLAccounts) {
        return refAgencyGLAccountEntityRepository.saveAll(
                        agencyGLAccounts
                                .stream()
                                .map(refAgencyGLAccountMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(refAgencyGLAccountMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<RefAgencyGLAccount> readAll() {
        return refAgencyGLAccountEntityRepository.findAll()
                .stream()
                .map(refAgencyGLAccountMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refAgencyGLAccountEntityRepository.deleteAll();
    }
}
