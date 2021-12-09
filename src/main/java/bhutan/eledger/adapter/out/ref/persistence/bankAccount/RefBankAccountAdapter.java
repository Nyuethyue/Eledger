package bhutan.eledger.adapter.out.ref.persistence.bankAccount;


import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefBankAccountAdapter implements RefBankAccountRepositoryPort {

    private final RefBankAccountMapper refBankAccountMapper;
    private final RefBankAccountRepository refBankAccountRepository;

    @Override
    public Long create(RefBankAccount refBankAccount) {
        RefBankAccountEntity refBankAccountEntity =
                refBankAccountMapper.mapToEntity(refBankAccount);
        return refBankAccountRepository.save(refBankAccountEntity).getId();
    }

    @Override
    public Collection<RefBankAccount> readAll() {
        return refBankAccountRepository.findAll()
                .stream()
                .map(refBankAccountMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refBankAccountRepository.deleteAll();
    }

    @Override
    public Optional<RefBankAccount> readById(Long id) {
        return refBankAccountRepository.findById(id)
                .map(refBankAccountMapper::mapToDomain);
    }

    @Override
    public boolean isOpenBankAccountExists(RefBankAccount refBankAccount) {
        return refBankAccountRepository.existsByCodeAndEndOfValidityNullOrEndOfValidity(refBankAccount.getCode(), LocalDate.now(), refBankAccount.getValidityPeriod().getStart());
    }

    @Override
    public Optional<RefBankAccount> readAllByBranchId(Long branchId) {
        return refBankAccountRepository.readAllByBranchId(branchId)
                .map(refBankAccountMapper::mapToDomain);
    }

    @Override
    public  Collection<RefBankAccount> readByCode(String code) {
        return refBankAccountRepository.findByCode(code)
                .stream()
                .map(refBankAccountMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }


    @Override
    public void setPrimaryFlagById(Long id, Boolean flag) {
        refBankAccountRepository.setPrimaryFlagById(id, flag);
    }

    @Override
    public Long readIdByGlCodeAndFlag(String code, Boolean flag) {
        return refBankAccountRepository.readIdByGlCodeAndFlag(code, flag);
    }


}
