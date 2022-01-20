package bhutan.eledger.adapter.out.ref.persistence.holidaydate;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import org.springframework.stereotype.Component;

@Component
class HolidayDateMapper {

    HolidayDateEntity mapToEntity(HolidayDate holidayDate) {

        HolidayDateEntity holidayDateEntity = new HolidayDateEntity(
                holidayDate.getId(),
                holidayDate.getYear(),
                holidayDate.getValidityPeriod().getStart(),
                holidayDate.getValidityPeriod().getEnd(),
                holidayDate.getIsValidForOneYear()
        );

        holidayDate.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new HolidayDateDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(holidayDateEntity::addToDescriptions);

        return holidayDateEntity;
    }

    HolidayDate mapToDomain(HolidayDateEntity holidayDateEntity) {
        return HolidayDate.withId(
                holidayDateEntity.getId(),
                holidayDateEntity.getYear(),
                ValidityPeriod.of(
                        holidayDateEntity.getStartOfValidity(),
                        holidayDateEntity.getEndOfValidity()
                ),
                holidayDateEntity.getIsValidForOneYear(),
                Multilingual.of(holidayDateEntity.getDescriptions())
        );
    }
}
