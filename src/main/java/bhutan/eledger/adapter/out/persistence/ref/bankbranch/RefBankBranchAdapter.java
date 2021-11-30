package bhutan.eledger.adapter.out.persistence.ref.bankbranch;

import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import bhutan.eledger.domain.ref.bank.RefBank;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefBankBranchAdapter implements RefBankBranchRepositoryPort {

    private final RefBankBranchMapper refBankBranchMapper;
    private final RefBankBranchRepository refBankBranchRepository;

    @Override
    public Long create(RefBankBranch refBankBranch) {
        RefBankBranchEntity refBankBranchEntity =
                refBankBranchMapper.mapToEntity(refBankBranch);

        return refBankBranchRepository.save(refBankBranchEntity).getId();
    }

    @Override
    public Collection<RefBankBranch> readAll() {
        return refBankBranchRepository.findAll()
                .stream()
                .map(refBankBranchMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refBankBranchRepository.deleteAll();
    }

    @Override
    public Optional<RefBankBranch> readById(Long id) {
        return refBankBranchRepository.findById(id)
                .map(refBankBranchMapper::mapToDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return refBankBranchRepository.existsByCode(code);
    }


    @Override
    public Collection<RefBankBranch> readAllByBankId(Long bankId) {
        return refBankBranchRepository.readAllByBankId(bankId)
                .stream()
                .map(refBankBranchMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsById(Long id) {
        return refBankBranchRepository.existsById(id);
    }

    @Override
    public boolean isOpenBranchExists(RefBankBranch refBankBranch) {
        return refBankBranchRepository.existsByCodeAndEndOfValidityNullOrEndOfValidity(refBankBranch.getCode(), LocalDate.now(), refBankBranch.getValidityPeriod().getStart());
    }
}
