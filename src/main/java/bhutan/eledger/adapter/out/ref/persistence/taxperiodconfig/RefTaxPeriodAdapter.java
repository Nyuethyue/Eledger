package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;


import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;


@Component
@RequiredArgsConstructor
class RefTaxPeriodAdapter implements RefTaxPeriodRepositoryPort {

    private final RefTaxPeriodMapper refTaxPeriodMapper;
    private final RefTaxPeriodEntityRepository refTaxPeriodEntityRepository;


    @Override
    public Long create(RefBank refBank) {
        return null;
    }

    @Override
    public Collection<RefBank> readAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<RefBank> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<RefBank> readByCode(String code) {
        return Optional.empty();
    }

    @Override
    public boolean existsByCode(String code) {
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
