package bhutan.eledger.adapter.out.ref.persistence.agency;

import bhutan.eledger.application.port.out.ref.agency.RefAgencyRepositoryPort;
import bhutan.eledger.domain.ref.agency.RefAgency;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RefAgencyAdapter implements RefAgencyRepositoryPort {

    private final RefAgencyMapper refAgencyMapper;
    private final RefAgencyEntityRepository refAgencyEntityRepository;

    @Override
    public Long create(RefAgency refAgency) {
        RefAgencyEntity refAgencyEntity =
                refAgencyMapper.mapToEntity(refAgency);

        return refAgencyEntityRepository.save(refAgencyEntity).getId();
    }

    @Override
    public Collection<RefAgency> readAll() {
        return refAgencyEntityRepository.findAll()
                .stream()
                .map(refAgencyMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        refAgencyEntityRepository.deleteAll();
    }

    @Override
    public boolean isOpenAgencyExists(RefAgency refAgency) {
        return refAgencyEntityRepository.existsByCodeAndEndOfValidityNullOrEndOfValidity(refAgency.getCode(), LocalDate.now(), refAgency.getValidityPeriod().getStart());
    }
}
