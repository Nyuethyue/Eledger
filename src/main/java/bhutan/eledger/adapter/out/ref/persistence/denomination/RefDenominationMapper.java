package bhutan.eledger.adapter.out.ref.persistence.denomination;

import bhutan.eledger.domain.ref.denomination.RefDenomination;
import org.springframework.stereotype.Component;

@Component
class RefDenominationMapper {

    RefDenominationEntity mapToEntity(RefDenomination refDenomination) {
        return new RefDenominationEntity(
                refDenomination.getId(),
                refDenomination.getValue()
        );
    }

    RefDenomination mapToDomain(RefDenominationEntity refCurrencyEntity) {
        return RefDenomination.withId(
                refCurrencyEntity.getId(),
                refCurrencyEntity.getValue()
        );
    }
}
