package bhutan.eledger.configuration.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

@JsonComponent
class MonthDaySerializerDeserializer {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd");

    public static class MonthDaySerializer
            extends JsonSerializer<MonthDay> {

        @Override
        public void serialize(MonthDay monthDay, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(dateFormatter.format(monthDay));
        }
    }

    public static class UserJsonDeserializer
            extends JsonDeserializer<MonthDay> {

        @Override
        public MonthDay deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext)
                throws IOException {
            return MonthDay.parse(jsonParser.getCodec().readValue(jsonParser, String.class), dateFormatter);
        }
    }
}
