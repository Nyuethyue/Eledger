package bhutan.eledger.configuration.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

@JsonComponent
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class MonthDaySerializerDeserializer {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");

    public static class MonthDaySerializer
            extends JsonSerializer<MonthDay> {

        @Override
        public void serialize(MonthDay monthDay, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(DATE_TIME_FORMATTER.format(monthDay));
        }
    }

    public static class UserJsonDeserializer
            extends JsonDeserializer<MonthDay> {

        @Override
        public MonthDay deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext)
                throws IOException {
            return MonthDay.parse(jsonParser.getCodec().readValue(jsonParser, String.class), DATE_TIME_FORMATTER);
        }
    }
}
