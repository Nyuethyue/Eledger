package bhutan.eledger.domain.eledger.transaction;

import am.iunetworks.lib.multilingual.core.Translation;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//todo repackage?
@Data(staticConstructor = "of")
public class PaymentAdviceData {

    private final String drn;
    private final Taxpayer taxpayer;
    private final LocalDate dueDate;
    private final Period period;
    private final Collection<PayableLine> payableLines;

    @Data
    public static class PayableLine {

        private final BigDecimal amount;
        private final GLAccount glAccount;
        private final Long transactionId;
    }

    @Data
    public static class GLAccount {
        private final String code;
        private final Map<String, String> descriptions;

        public GLAccount(String code, List<TranslationCommand> descriptions) {
            this.code = code;
            this.descriptions = descriptions
                    .stream()
                    .collect(Collectors.toMap(TranslationCommand::getLanguageCode, TranslationCommand::getValue));
        }
    }

    @Data(staticConstructor = "of")
    public static class Period {
        private final String year;
        private final String segment;
    }

    @Data(staticConstructor = "of")
    public static class Taxpayer {
        private final String tpn;
        private final String name;
    }

    @Data
    public static class TranslationCommand implements Translation {
        private final String languageCode;
        private final String value;
    }
}
