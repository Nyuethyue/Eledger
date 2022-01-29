package bhutan.eledger.adapter.out.ref.persistence.holidaydate;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
import org.springframework.stereotype.Component;

import java.time.MonthDay;

@Component
class RefHolidayDateMapper {

    RefHolidayDateEntity mapToEntity(RefHolidayDate holidayDate) {

        RefHolidayDateEntity refHolidayDateEntity = new RefHolidayDateEntity(
                holidayDate.getId(),
                holidayDate.getYear(),
                holidayDate.getStartOfHoliday().getDayOfMonth(),
                holidayDate.getStartOfHoliday().getMonthValue(),
                holidayDate.getEndOfHoliday().getDayOfMonth(),
                holidayDate.getEndOfHoliday().getMonthValue(),
                holidayDate.getValidityPeriod().getStart(),
                holidayDate.getValidityPeriod().getEnd()
        );
        holidayDate.getDescription()
                .getTranslations()
                .stream()
                .map(t ->
                        new RefHolidayDateDescriptionEntity(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue()
                        )
                )
                .forEach(refHolidayDateEntity::addToDescriptions);

        return refHolidayDateEntity;
    }

    RefHolidayDate mapToDomain(RefHolidayDateEntity holidayDateEntity) {
        return RefHolidayDate.withId(
                holidayDateEntity.getId(),
                holidayDateEntity.getYear(),
                MonthDay.of(
                        holidayDateEntity.getStartMonthOfHoliday(),
                        holidayDateEntity.getStartDayOfHoliday()
                ),
                MonthDay.of(
                        holidayDateEntity.getEndMonthOfHoliday(),
                        holidayDateEntity.getEndDayOfHoliday()
                ),
                ValidityPeriod.of(
                        holidayDateEntity.getStartOfValidity(),
                        holidayDateEntity.getEndOfValidity()
                ),
                Multilingual.of(holidayDateEntity.getDescriptions())
        );
    }
}
