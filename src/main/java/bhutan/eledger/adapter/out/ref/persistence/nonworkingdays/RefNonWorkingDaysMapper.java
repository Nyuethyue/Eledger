package bhutan.eledger.adapter.out.ref.persistence.nonworkingdays;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import org.springframework.stereotype.Component;

import java.time.MonthDay;

@Component
class RefNonWorkingDaysMapper {

    RefNonWorkingDaysEntity mapToEntity(RefNonWorkingDays nonWorkingDays) {

        RefNonWorkingDaysEntity refNonWorkingDaysEntity = new RefNonWorkingDaysEntity(
                nonWorkingDays.getId(),
                nonWorkingDays.getCode(),
                nonWorkingDays.getYear(),
                nonWorkingDays.getStartDay().getDayOfMonth(),
                nonWorkingDays.getStartDay().getMonthValue(),
                nonWorkingDays.getEndDay().getDayOfMonth(),
                nonWorkingDays.getEndDay().getMonthValue(),
                nonWorkingDays.getValidityPeriod().getStart(),
                nonWorkingDays.getValidityPeriod().getEnd()
        );
        nonWorkingDays.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefNonWorkingDaysDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refNonWorkingDaysEntity::addToDescriptions);

        return refNonWorkingDaysEntity;
    }

    RefNonWorkingDays mapToDomain(RefNonWorkingDaysEntity nonWorkingDaysEntity) {
        return RefNonWorkingDays.withId(
                nonWorkingDaysEntity.getId(),
                nonWorkingDaysEntity.getCode(),
                nonWorkingDaysEntity.getYear(),
                MonthDay.of(
                        nonWorkingDaysEntity.getStartMonth(),
                        nonWorkingDaysEntity.getStartDay()
                ),
                MonthDay.of(
                        nonWorkingDaysEntity.getEndMonth(),
                        nonWorkingDaysEntity.getEndDay()
                ),
                ValidityPeriod.of(
                        nonWorkingDaysEntity.getStartOfValidity(),
                        nonWorkingDaysEntity.getEndOfValidity()
                ),
                Multilingual.of(nonWorkingDaysEntity.getDescriptions())
        );
    }
}
