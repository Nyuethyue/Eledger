package bhutan.eledger.domain.eledger.config.property;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "withId")
public class Property {
    private final Long id;
    private final String code;
    private final DataType dataType;
    private final Multilingual description;

    public static Property withoutId(String code, DataType dataType, Multilingual description) {
        return new Property(
                null,
                code,
                dataType,
                description
        );
    }
}
