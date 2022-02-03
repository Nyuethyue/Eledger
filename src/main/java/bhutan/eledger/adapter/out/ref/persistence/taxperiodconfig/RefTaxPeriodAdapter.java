package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
class RefTaxPeriodAdapter implements RefTaxPeriodRepositoryPort {

    private final RefTaxPeriodMapper refTaxPeriodMapper;
    private final RefTaxPeriodEntityRepository refTaxPeriodEntityRepository;

    @Override
    public Long upsert(RefTaxPeriodConfig b) {
        var conf =
                readBy(b.getTaxTypeCode(), b.getCalendarYear(), b.getTaxPeriodTypeId(), b.getTransactionTypeId());
        if(conf.isPresent()) {
            return refTaxPeriodEntityRepository.save(refTaxPeriodMapper.mapToEntity(b)).getId();
        } else {
            return refTaxPeriodEntityRepository.save(refTaxPeriodMapper.mapToEntity(b)).getId();
        }
    }

    @Override
    public Optional<RefTaxPeriodConfig> readBy(String taxTypeCode, Integer calendarYear, Long taxPeriodTypeId, Long transactionTypeId) {
        var result =
                refTaxPeriodEntityRepository.readBy(taxTypeCode, calendarYear, taxPeriodTypeId, transactionTypeId);
        if(result.isPresent()) {
            return Optional.of(refTaxPeriodMapper.mapToDomain(result.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {

    }
}
