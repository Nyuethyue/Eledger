package bhutan.eledger.domain.eledger.config.property;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@ToString
public class Property {
    private final Long id;
    private final String code;
    private final DataType dataType;
    private final String value;
    private final ValidityPeriod<LocalDate> validityPeriod;
    private final Multilingual description;

    public static Property withId(Long id, String code, DataType dataType, String value, ValidityPeriod<LocalDate> validityPeriod, Multilingual description) {
        return new Property(
                id,
                code,
                dataType,
                value,
                validityPeriod,
                description
        );
    }

    public static Property withoutId(String code, DataType dataType, String value, ValidityPeriod<LocalDate> validityPeriod, Multilingual description) {
        return new Property(
                null,
                code,
                dataType,
                value,
                validityPeriod,
                description
        );
    }

    private static PropertyBuilder builder() {
        return new PropertyBuilder();
    }

    public static PropertyBuilder builder(String code, ValidityPeriod<LocalDate> validityPeriod) {
        return builder().code(code).validityPeriod(validityPeriod);
    }

    public PropertyBuilder toBuilder() {
        return builder(code, validityPeriod)
                .id(id)
                .dataType(dataType)
                .value(value)
                .description(description);
    }
}
