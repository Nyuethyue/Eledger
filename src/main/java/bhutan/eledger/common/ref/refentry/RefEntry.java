package bhutan.eledger.common.ref.refentry;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data(staticConstructor = "of")
public class RefEntry {

    private final Long id;

    private final String code;

    private final Multilingual description;

    private final Map<String, String> attributes;

    public Map<String, String> getAttributes() {
        return attributes == null || attributes.isEmpty() ? Map.of() : attributes;
    }

    public static RefEntry withoutAttributes(Long id, String code, Multilingual description) {
        return of(id, code, description, Map.of());
    }

    RefEntry(Long id, String code, Multilingual description, Map<String, String> attributes) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.attributes = attributes;
    }

    public static RefEntryBuilder builder(Long id, String code) {
        return new RefEntryBuilder(id, code);
    }

    public static class RefEntryBuilder {
        private final Long id;
        private final String code;
        private Multilingual description;
        private Map<String, String> attributes;

        RefEntryBuilder(Long id, String code) {
            this.id = id;
            this.code = code;
        }


        public RefEntryBuilder description(Multilingual description) {
            this.description = description;
            return this;
        }

        public RefEntryBuilder attributes(Map<String, String> attributes) {
            this.attributes = new HashMap<>(attributes);
            return this;
        }

        public RefEntryBuilder addAttribute(String key, String value) {
            if (attributes == null || attributes.isEmpty()) {
                attributes = new HashMap<>();
            }

            attributes.put(key, value);

            return this;
        }

        public RefEntry build() {
            return new RefEntry(id, code, description, attributes);
        }

        public String toString() {
            return "RefEntry.RefEntryBuilder(id=" + this.id + ", code=" + this.code + ", description=" + this.description + ", attributes=" + this.attributes + ")";
        }
    }
}
