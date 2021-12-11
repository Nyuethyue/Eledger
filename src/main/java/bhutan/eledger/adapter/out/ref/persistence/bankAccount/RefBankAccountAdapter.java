package bhutan.eledger.adapter.out.ref.persistence.bankAccount;


import am.iunetworks.lib.common.validation.RecordNotFoundException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import am.iunetworks.lib.multilingual.core.Translation;
import bhutan.eledger.application.port.out.epayment.config.bank.GetPrimaryBankAccountRefEntryByGLCodeAccountPort;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefBankAccountAdapter implements RefBankAccountRepositoryPort, GetPrimaryBankAccountRefEntryByGLCodeAccountPort {

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
    public Optional<RefBankAccount> readAllByBranchId(Long branchId, LocalDate currentDate) {
        return refBankAccountRepository.readAllByBranchId(branchId, currentDate)
                .map(refBankAccountMapper::mapToDomain);
    }

    @Override
    public Optional<RefBankAccount> readByCode(String code, LocalDate currentDate) {
        return refBankAccountRepository.readByCode(code, currentDate)
                .map(refBankAccountMapper::mapToDomain);
    }


    @Override
    public void setPrimaryFlagById(Long id, Boolean flag) {
        refBankAccountRepository.setPrimaryFlagById(id, flag);
    }

    @Override
    public Long readIdByGlCodeAndFlag(String code, Boolean flag) {
        return refBankAccountRepository.readIdByGlCodeAndFlag(code, flag);
    }


    @Override
    public RefEntry getPrimaryBankAccountByGLCode(String glCode, LocalDate validityDate) {
        return refBankAccountRepository.getPrimaryBankAccountByGLCode(glCode, validityDate)
                .map(bacc ->
                        RefEntry.withoutAttributes(
                                bacc.getId(),
                                bacc.getCode(),
                                Multilingual.of(
                                        bacc.getDescriptions()
                                                .stream()
                                                .map(t ->
                                                        Translation.of(
                                                                t.getLanguageCode(),
                                                                t.getValue()
                                                        )
                                                )
                                                .collect(Collectors.toUnmodifiableSet())
                                )
                        )
                )
                .orElseThrow(
                        () -> new RecordNotFoundException("Valid primary bank account not found by code: " + glCode)
                );
    }
}
